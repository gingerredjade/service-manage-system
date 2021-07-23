package com.smp.admin.model.po.sys;

import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户角色表
 * 	用户和角色的中间表。
 * 	通过用户ID和角色ID分别跟用户表和角色表关联。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_user_role")
@EntityListeners(AuditingEntityListener.class)
public class SysUserRoleDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private Long userId;	// 用户ID

	@Column
	private Long roleId;	// 角色ID

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
