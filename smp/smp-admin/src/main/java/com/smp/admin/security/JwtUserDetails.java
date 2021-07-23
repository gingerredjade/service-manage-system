package com.smp.admin.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全用户模型
 *
 * 是对认证信息的封装，实现Spring Security，提供UserDetails接口，主要包含用户名、密码、加密盐和权限信息。
 *
 * 要使用Spring Security实现对用户的权限控制，首先需要实现一个简单的User对象实现UserDetails接口
 * UserDetails接口负责提供核心用户的信息，
 * 如果你只需要用户登录的账号密码，不需要其他信息，如验证码等，
 * 那么可以直接使用Spring Security默认提供的User类，而不需要自己实现。
 *
 * JwtUserDetails是要使用到的User对象，其中包含了记住我，验证码等登录信息，
 * 因为Spring Security整合JWT本质上就是用自己自定义的登录过滤器，
 * 去替换Spring Security原生的登录过了长期，这样的话，原生的记住我功能就会无法使用，
 * 所以在JwtUserDetails对象里添加了记住我的信息，用来自己实现这个功能。
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username; 		// 用户名
	private String password; 		// 密码
	private String salt; 			// 加密盐
	private Boolean rememberMe; 	// 记住我（暂未使用）
	private String verifyCode; 		// 验证码（暂未使用）
	private String power; 			// （暂未使用）
	private Collection<? extends GrantedAuthority> authorities; // 权限信息

	JwtUserDetails(String username, String password, String salt,
				   Boolean rememberMe, String verifyCode, String power,
				   Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.rememberMe = rememberMe;
		this.verifyCode = verifyCode;
		this.power = power;
		this.authorities = authorities;
	}

	JwtUserDetails(String username, String password, String salt,
				   Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getSalt() {
		return salt;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
