package com.smp.admin.security;

import com.smp.admin.model.po.sys.SysUserDO;
import com.smp.admin.service.iface.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户登录认证信息查询（用户查找服务）
 *
 * 	登录验证器在进行时是要从数据库获取用户信息进行匹配的，
 * 	而这个获取用户信息的任务是通过Spring Security的UserDetailsService组件来完成的。
 *
 * 	在security包下新建一个UserDetailsServiceImpl并实现UserDetailsService接口，覆写其中的方法loadUserByUsername，
 * 	查询用户的密码信息和权限信息并封装到UserDetails的实现类对象，作为结果JwtUserDetails返回给DaoAuthenticationProvider做进一步处理。
 *
 * 	在WebSecurityConfig中注入时需要这个Service实现类
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final SysUserService sysUserService;
	@Autowired
	public UserDetailsServiceImpl(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUserDO user = sysUserService.getByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("该用户不存在");
		}
		/*
		 * 用户权限列表
		 * 	根据用户拥有的权限标识如@PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
		 */
		Set<String> permissions = sysUserService.getPermissions(username);
		List<GrantedAuthority> grantedAuthorityList =
			permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
		return new JwtUserDetails(user.getUsername(), user.getPassword(), user.getSalt(), grantedAuthorityList);

		/*return new JwtUserDetails(
			user.getUsername(), user.getPassword(), user.getSalt(),
			false, "", "",
			grantedAuthorityList);*/
	}
}
