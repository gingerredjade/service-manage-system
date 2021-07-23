package com.smp.admin.model.po.sys;

import com.smp.admin.constant.enums.StatusEnum;
import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 字典表
 * 	主要存储系统常用的枚举类型数据，主要包含编号、标签、数据值、类型等字段。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */

@Entity
@Table(name = "sys_dict")
@EntityListeners(AuditingEntityListener.class)
public class SysDictDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;


	/*@Lob
	@Column(columnDefinition = "TEXT")*/
	@Column
	private String value;			// 数据值（字典值）

	@Column
	private String label;			// 标签（字典标识）

	@Column
	private String title;			// 字典标题（暂未使用）

	@Column
	private String type;			// 类型（字典类型：1键值对k-v,2 k-map options）

	@Column
	private String description;		// 描述

	@Column
	private Long sort;				// 排序(升序)

	@Column
	private String remarks;			// 备注信息

	@Column
	private Byte delFlag = StatusEnum.OK.getCode();			// 是否删除(3:已删除, 1：正常)

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Byte getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Byte delFlag) {
		this.delFlag = delFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
