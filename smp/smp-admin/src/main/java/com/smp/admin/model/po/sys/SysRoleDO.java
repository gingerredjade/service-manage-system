package com.smp.admin.model.po.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色表
 * 	角色代表用户角色，用户拥有角色，角色拥有菜单，菜单拥有权限标识，所以不同角色拥有不同的权限。
 * 	角色表主要有编号、角色名、备注等字段。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_role")
@EntityListeners(AuditingEntityListener.class)
public class SysRoleDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String name;	// 角色名称

	@Column
	private String flag;	// 角色标识（未使用）

	@Column
	private String remark;	// 备注信息

	@Column
	private Byte delFlag = StatusEnum.OK.getCode();	// 是否删除(3：已删除, 1：正常)


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
