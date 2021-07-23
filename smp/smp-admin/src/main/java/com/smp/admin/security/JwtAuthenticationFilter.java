package com.smp.admin.security;

import com.smp.admin.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录认证过滤器
 * 	负责登录认证时检查并生产令牌保存到上下文，接口权限认证过程时，系统从上下文获取令牌校验接口访问权限，
 * 	在security包中创建JwtAuthenticationFilter并继承BasicAuthenticationFilter，覆写其中的doFilterInternal方法进行Token校验。
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain);

		/*
		 * 获取token，并检查登录状态
		 * 	逻辑抽取到了SecurityUtils的checkAuthentication方法中，
		 *  checkAuthentication通过JwtTokenUtils的方法获取认证信息并保存到Spring Security上下文。
		 *  JwtTokenUtils的getAuthenticationeFromToken方法获取并校验请求携带的令牌。
		 */
		SecurityUtils.checkAuthentication(request);
		chain.doFilter(request, response);
	}
}
