package com.smp.admin.aspect;

import com.alibaba.fastjson.JSONObject;
import com.smp.admin.model.po.sys.SysLogDO;
import com.smp.admin.service.iface.sys.SysLogService;
import com.smp.admin.util.IPUtils;
import com.smp.admin.util.SecurityUtils;
import com.smp.common.util.StringUtils;
import com.smp.common.util.http.HttpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志，切面(通知+切点)处理类，记录操作日志
 * 	SpringBoot AOP(面向切面编程，关注点分离技术)
 * 		execution表达式
 *
 * 	AOP通过对既有程序定义一个切点，然后在其前后切入不同的执行内容，切面最终需要织入Weave到业务功能中。
 * 	。
 * @author Hongyu Jiang
 * @since  Apr. 27 2021
 */
@Aspect
@Component
public class SysLogAspect {

	private final SysLogService sysLogService;

	@Autowired
	public SysLogAspect(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	/**
	 * 定义切入点PointCut
	 */
	@Pointcut("execution(* com.smp.admin.service.iface.*.*.*(..))")
	public void logPointCut() {

	}

	/**
	 * 因为要在调用前后都切入代码，才能算出来这之间时间差。因此，定义的是一个Around类型的Advice.
	 *
	 * 环绕通知，目标方法执行前后都需要执行通知定义的任务
	 * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
	 * 环绕通知的第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
	 *
	 * @param point 切点
	 * @return
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {

		long beginTime = System.currentTimeMillis();
		// 执行方法（目标方法调用）
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		String userName = SecurityUtils.getUsername();
		if (!StringUtils.isBlank(userName)) {
			saveSysLog(point, time);
		}
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		String userName = SecurityUtils.getUsername();
		if(joinPoint.getTarget() instanceof SysLogService) {
			return ;
		}
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		SysLogDO sysLog = new SysLogDO();

//		Method method = signature.getMethod();
//		com.smp.admin.annotation.SysLogDO syslogAnno = method.getAnnotation(com.smp.admin.annotation.SysLogDO.class);
//		if(syslogAnno != null){
//			//注解上的描述
//			sysLog.setOperation(syslogAnno.value());
//		}

		// 请求的方法名(环绕通知的目标方法名)
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args[0]);
			if(params.length() > 200) {
				params = params.substring(0, 200) + "...";
			}
			sysLog.setParams(params);
		} catch (Exception e){
		}

		// 获取request
		HttpServletRequest request = HttpUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		// 用户名
		sysLog.setUserName(userName);

		// 执行时长(毫秒)
		sysLog.setTime(time);

		// 保存系统日志
		sysLogService.save(sysLog);
	}


}
