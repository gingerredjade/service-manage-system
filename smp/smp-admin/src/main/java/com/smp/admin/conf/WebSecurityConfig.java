package com.smp.admin.conf;

import com.smp.admin.security.JwtAuthenticationFilter;
import com.smp.admin.security.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * Spring Security配置
 * 	在Web应用开发中，安全一直是非常重要的一个方面。
 * 	Spring Security基于Spring框架，提供了一套Web应用安全性的完整解决方案。
 * 	JWT（JSON Web Token）是当前比较主流的Token令牌生成方案，非常适合作为登录和授权认证的凭证。
 * 	采用Spring Security并结合JWT实现用户认证（Authentication）和用户授权（Authorization）两个主要本分的安全内容,构建后端API接口。
 *
 * 	在config包下新建一个Spring Security的配置类WebSecurityConfig，
 * 	主要是进行一些安全相关的配置，比如权限URL匹配策略、认证过滤器配置、定制身份验证组件、开启权限认证注解等
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
@Configuration
@EnableWebSecurity	// 开启Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true)	// 开启权限注解，如：@PreAuthorize注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// 需要先编写UserDetailsServiceImpl类实现UserDetailsService接口提供Service
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		// 使用自定义身份验证组件
		auth.authenticationProvider(new JwtAuthenticationProvider(userDetailsService));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);

		// 禁用csrf，由于使用的是JWT，我们这里不需要csrf
		http.cors().and().csrf().disable()
			.authorizeRequests()
			// 跨域预检请求
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			// web jars
			.antMatchers("/webjars/**").permitAll()
			// 查看SQL监控（druid）
			.antMatchers("/druid/**").permitAll()
			// 首页和登录页面
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			// swagger
			.antMatchers("/swagger-ui.html").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/v2/api-docs").permitAll()
			.antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
			// 验证码
			.antMatchers("/captcha.jpg**").permitAll()
			// 服务监控
			.antMatchers("/actuator/**").permitAll()
			// 其他所有请求需要身份认证
			.anyRequest().authenticated();//.authenticated();permitAll
		// 禁用X-Frame-Options设置
		http.headers().frameOptions().disable();
		// 退出登录处理器
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		// token验证过滤器
		http.addFilterBefore(
			new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		web.ignoring().antMatchers("/smp-show/**");
	}
}
