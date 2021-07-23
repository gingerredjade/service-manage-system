package com.smp.admin.model.po.sys;

import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色机构表
 * 	角色和机构的中间表。
 * 	通过角色ID和机构ID分别与角色表和机构表关联。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_role_dept")
@EntityListeners(AuditingEntityListener.class)
public class SysRoleDeptDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private Long roleId;	// 角色ID

	@Column
	private Long deptId;	// 机构ID


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}
