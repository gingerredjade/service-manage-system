package com.smp.admin.security;

import com.smp.common.util.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 身份认证提供者(身份认证组件)
 * 	Spring Security的登录验证是交由ProviderManager负责的，
 * 	ProviderManager在实际验证的时候又会通过调用AuthenticationProvider的authenticate方法来进行认证。
 *  数据库类型的默认实现方案是DaoAuthenticationProvider。
 *
 * 	通过继承DaoAuthenticationProvider定制默认的登录认证逻辑
 *
 * 	在security包下新建验证器JwtAuthenticationProvider继承DaoAuthenticationProvider，
 * 	覆盖实现additionalAuthenticationChecks方法进行密码匹配，
 * 	这里没有使用默认的密码认证器（我们使用salt来对密码加密，默认密码验证器没有加盐），
 * 	所以在这里定制了自己的密码校验逻辑，
 * 	当然你也可以通过直接覆写authenticate方法来完成更大范围的登录认证需求定制。
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

	public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
		setUserDetailsService(userDetailsService);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		//super.additionalAuthenticationChecks(userDetails, authentication);

		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		String salt = ((JwtUserDetails) userDetails).getSalt();
		// 覆写密码验证逻辑
		if (!new PasswordEncoder(salt).matches(userDetails.getPassword(), presentedPassword)) {
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

	}
}
