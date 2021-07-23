package com.smp.admin.controller.sys;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.po.sys.SysUserDO;
import com.smp.admin.model.vo.LoginBean;
import com.smp.admin.security.JwtAuthenticationToken;
import com.smp.admin.service.iface.sys.SysLoginLogService;
import com.smp.admin.service.iface.sys.SysUserService;
import com.smp.admin.util.IPUtils;
import com.smp.admin.util.SecurityUtils;
import com.smp.common.api.CommonResult;
import com.smp.common.api.CommonResultUtil;
import com.smp.common.util.IOUtils;
import com.smp.common.util.password.PasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录控制器Controller
 * 	提供系统登录相关的API，在其中添加验证码生成接口
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
@CrossOrigin(allowCredentials = "true", origins = "*")
@RestController
@Api(tags = "登录管理", value = "SysLoginController",
	description = "系统登录相关的API")
public class SysLoginController {

	private final Producer kaptchaProducer;
	private final AuthenticationManager authenticationManager;

	private final SysUserService sysUserService;
	private final SysLoginLogService sysLoginLogService;

	@Autowired
	public SysLoginController(SysUserService sysUserService,
							  SysLoginLogService sysLoginLogService,
							  Producer kaptchaProducer,
							  AuthenticationManager authenticationManager) {
		this.sysUserService = sysUserService;
		this.sysLoginLogService = sysLoginLogService;

		this.kaptchaProducer = kaptchaProducer;
		this.authenticationManager = authenticationManager;
	}

	/**
	 * 验证码请求接口
	 */
	@ApiOperation(value = "生成验证码",
		notes = "生成验证码")
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(30*60); // 以秒为单位
		response.setDateHeader("Expires", 0);
		//System.out.println("[生成验证码存入session时的sessionId]"+session.getId());

		// 告诉浏览器不要缓存,防止生成同样的验证码图片
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		// 返回信息为jpg
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = kaptchaProducer.createText();
		// 生成图片验证码
		BufferedImage image = kaptchaProducer.createImage(text);
		// 保存验证码到 session,等待比对
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录接口
	 *
	 * 1、客户端使用用户名、密码、验证码请求登录
	 * 2、服务端收到请求去验证用户名、密码、验证码
	 * 3、验证成功后，服务端签发一个token并把token发送给客户端
	 * 4、客户端收到token后，将其存储（如放在cookie或者localStorage里）
	 * 5、客户端每次向服务端请求资源的时候需要带着服务端签发的token
	 * 6、服务端收到请求，然后去验证客户端请求里携带的token，若验证成功就向客户端返回请求的数据
	 */
	@PostMapping("/login")
	public CommonResult login(@RequestBody LoginBean loginBean, HttpServletRequest request,
							  HttpServletResponse response) {
		String username = loginBean.getAccount();
		String password = loginBean.getPassword();
		/*String captcha = loginBean.getCaptcha();

		HttpSession session = request.getSession();
		System.out.println("[登录验证时获取的session的sessionId]"+session.getId());*/

		/*
		 * 1-- 上下文获取验证码信息并验证（从session中获取之前保存的验证码跟前台传来的验证码进行匹配）
		 * 	1--1 验证码是否过期
		 * 	1--2 验证码是否正确
		 */
		// 获取生成的验证码
		/*String verifyKaptchaExpected = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		// 1--1 验证码是否过期
		if (verifyKaptchaExpected == null) {
			return CommonResultUtil.failed("验证码已失效，请刷新验证码");
		}
		// 1--2 验证码是否正确
		if (!captcha.equals(verifyKaptchaExpected)) {
			return CommonResultUtil.failed("验证码不正确");
		}*/

		/*
		 * 2-- 用户信息验证
		 */
		// 根据用户名查询用户信息
		SysUserDO user = sysUserService.getByName(username);

		// 账号不存在、密码错误
		if (user == null) {
			return CommonResultUtil.failed("账号不存在");
		}
		if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
			return CommonResultUtil.failed("密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == StatusEnum.FREEZED.getCode()) {
			return CommonResultUtil.failed("账号已被"+StatusEnum.FREEZED.getMessage()+"，请联系管理员解锁");
		}

		/*
		 * 3-- 验证成功，签发JWT，记录登录日志
		 */
		// 系统登录认证
		JwtAuthenticationToken token = SecurityUtils.login(request, username, password, authenticationManager);

		// 记录登录日志
		sysLoginLogService.writeLoginLog(username, IPUtils.getIpAddr(request));

		return CommonResultUtil.success(token);
	}

	/*@ModelAttribute
	public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Set-Cookie", response.getHeader("Set-Cookie")+";HttpOnly;Secure;SameSite=None");
	}*/

}
