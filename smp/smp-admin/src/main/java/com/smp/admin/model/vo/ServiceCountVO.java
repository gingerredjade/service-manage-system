package com.smp.admin.model.vo;

/**
 * 服务统计管理Service
 * @author Hongyu Jiang
 * @since Sep. 9 2020
 */
public class ServiceCountVO {

	Long grossNumber;			// 全部服务数量
	Long releasedNumber; 		// 已发布服务数量
	Long unReleasedNumber; 		// 未发布服务数量
	Long auditedPassNumber; 	// 审核通过服务数量
	Long unAuditedNumber; 		// 待审核服务数量

	public Long getReleasedNumber() {
		return releasedNumber;
	}

	public void setReleasedNumber(Long releasedNumber) {
		this.releasedNumber = releasedNumber;
	}

	public Long getUnReleasedNumber() {
		return unReleasedNumber;
	}

	public void setUnReleasedNumber(Long unReleasedNumber) {
		this.unReleasedNumber = unReleasedNumber;
	}

	public Long getAuditedPassNumber() {
		return auditedPassNumber;
	}

	public void setAuditedPassNumber(Long auditedPassNumber) {
		this.auditedPassNumber = auditedPassNumber;
	}

	public Long getUnAuditedNumber() {
		return unAuditedNumber;
	}

	public void setUnAuditedNumber(Long unAuditedNumber) {
		this.unAuditedNumber = unAuditedNumber;
	}

	public Long getGrossNumber() {
		return grossNumber;
	}

	public void setGrossNumber(Long grossNumber) {
		this.grossNumber = grossNumber;
	}
}
