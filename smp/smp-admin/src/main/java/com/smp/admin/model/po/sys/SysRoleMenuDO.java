package com.smp.admin.model.po.sys;

import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色菜单表
 * 	角色和菜单的中间表。
 * 	通过角色ID和菜单ID分别和角色表和菜单表关联。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_role_menu")
@EntityListeners(AuditingEntityListener.class)
public class SysRoleMenuDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private Long roleId;	// 角色ID

	@Column
	private Long menuId;	// 菜单ID


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
