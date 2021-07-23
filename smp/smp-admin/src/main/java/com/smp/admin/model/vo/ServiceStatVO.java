package com.smp.admin.model.vo;

import java.util.List;

/**
 * 服务统计管理Service
 * @author Hongyu Jiang
 * @since Sep. 8 2020
 */
public class ServiceStatVO {

	private List<String> keys; 				// 统计名称
	private List<Long> values; 				// 统计结果
	private List<ServiceStatDetail> detail; // 统计名称及结果数据

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public List<Long> getValues() {
		return values;
	}

	public void setValues(List<Long> values) {
		this.values = values;
	}

	public List<ServiceStatDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<ServiceStatDetail> detail) {
		this.detail = detail;
	}
}
