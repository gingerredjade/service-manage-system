package com.smp.common.util.http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

/**
 * HTTP工具类
 * @author Hongyu Jiang
 * @since Jan 19, 2019
 */
public class HttpUtils {

	/**
	 * 获取HttpServletRequest对象
	 * @return 返回值
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	

	/**
	 * 输出信息到浏览器
	 * @param response  HttpServletResponse
	 * @param code code
	 * @param msg msg
	 * @throws IOException 异常
	 */
	public static void print(HttpServletResponse response, int code, String msg) throws IOException {
		response.setContentType("application/json; charset=utf-8");
        HttpResult result = HttpResult.error(code, msg);
        String json = JSONObject.toJSONString(result);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
	}
	
}
