package com.smp.admin.model.po.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 * 	菜单分为菜单目录、菜单和操作按钮3种类型，可以进行权限控制。
 * 	菜单表主要有编号、菜单名称、父菜单、菜单类型、菜单图标、菜单URL、菜单权限等字段。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_menu")
public class SysMenuDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;


	@Column
	private Long parentId;		// 父菜单ID,一级菜单为0

	@Column
	private String name;		// 菜单名称

	@Column
	private String url;			// 菜单URL,
								// 类型:1.普通页面（如用户管理,/sys/user）
								// 	   2.嵌套完整外部页面,以http(s)开头的链接
								//	   3.嵌套服务器页面,使用iframe:前缀+目标URL（如SQL监控，iframe:/druid/login.html,iframe前缀会替换成服务器地址）

	@Column
	private String perms;		// 授权(多个用逗号分隔，如sys:user:add,sys:user:edit) 用户权限标识

	@Column
	private Integer type;		// 类型(0:目录, 1:菜单, 2:按钮) - MenuTypeEnum

	@Column
	private String icon;		// 菜单图标

	@Column
	private Integer orderNum;	// 排序（sort）

	@Column
	private Byte delFlag = StatusEnum.OK.getCode();		// 是否删除(3:已删除, 1：正常)

	@Column
	private String external;		// 外部网页标识

	// 非数据库字段
	@Transient
	private String parentName;	// 父菜单名称

	// 非数据库字段
	@Transient
	private Integer level;		//

	// 非数据库字段
	@Transient
	private List<SysMenuDO> children;


	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<SysMenuDO> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuDO> children) {
		this.children = children;
	}

	public String getExternal() {
		return external;
	}

	public void setExternal(String external) {
		this.external = external;
	}
}
