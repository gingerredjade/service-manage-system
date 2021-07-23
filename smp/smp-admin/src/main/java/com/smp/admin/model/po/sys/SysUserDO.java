package com.smp.admin.model.po.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户表
 * 	包含用户信息，主要有编号、用户名、昵称、密码、邮箱、手机号等字段，
 * 	其中用户表通过表中deptId与机构表关联，表明所属机构。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_user")
@EntityListeners(AuditingEntityListener.class)
public class SysUserDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;


	@Column
	private String username;	// 用户名

	@Column
	private String nickName;	// 昵称

	@Column
	private Byte sex;			// 性别(1：男, 2：女)

	@Column
	private String avatar;		// 头像

	@Column
	private String password;	// 密码

	@Column
	private String salt;		// 加密盐

	@Column
	private String email;		// 邮箱

	@Column
	private String mobile;		// 手机号

	@Column
	private Byte status = StatusEnum.OK.getCode();		// 状态(2：禁用/冻结, 1：正常)

	@Column
	private Long deptId;		// 机构ID

	@Column
	private Byte delFlag = StatusEnum.OK.getCode();		// 是否删除(3:已删除, 1：正常)


	// 非数据库字段
	@Transient
	private String deptName;	// 机构名称

	// 非数据库字段
	@Transient
	private String roleNames;	// 角色名称

	// 非数据库字段
	@Transient
	private List<SysUserRoleDO> userRoles = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public List<SysUserRoleDO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<SysUserRoleDO> userRoles) {
		this.userRoles = userRoles;
	}
}
