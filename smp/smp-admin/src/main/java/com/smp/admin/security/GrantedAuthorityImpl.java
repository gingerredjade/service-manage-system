package com.smp.admin.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限封装
 *
 * 	GrantedAuthorityImpl实现Spring Security的GrantedAuthority接口，
 * 	是对权限的封装，
 * 	内部包含一个字符串类型的权限标识authority，对应 菜单表的perms字段的权限字符串，
 * 	比如用用户管理的增、删、改、查权限标志sys:user:view、sys:user:add、sys:user:edit、sys:user:delete。
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private String authority;

	public GrantedAuthorityImpl(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
