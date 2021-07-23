package com.smp.admin.model.po.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.BaseModel;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 机构表
 * 	机构代表一种组织机构，可以有子机构，用户归属于机构。
 * 	机构表主要有编号、机构名称、上级机构等字段。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Data
@Entity
@Table(name = "sys_dept")
@EntityListeners(AuditingEntityListener.class)
public class SysDeptDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column
	private String name;		// 机构名称

	@Column
	private Long parentId;		// 上级机构ID,一级机构为0

	@Column
	private Integer orderNum;	// 排序

	@Column
	private Byte delFlag = StatusEnum.OK.getCode();		// 是否删除(3：已删除, 1：正常)

	@Column
	private String address;		// 机构地址

	@Column
	private String website;		// 机构网站

	@Column
	private String contact;		// 机构联系人

	@Column
	private String phoneNumber;	// 机构联系电话

	@Column
	private String email;		// 机构联系人邮箱


	// 非数据库字段
	@Transient
	private List<SysDeptDO> children;	// 子机构列表

	// 非数据库字段
	@Transient
	private String parentName;			// 父级机构名称

	// 非数据库字段
	@Transient
	private Integer level;				// 级别



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SysDeptDO> getChildren() {
		return children;
	}

	public void setChildren(List<SysDeptDO> children) {
		this.children = children;
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
}
