package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * 审核状态枚举
 * @author Hongyu Jiang
 * @since  2020/4/26
 */
@Getter
public enum AuditTypeEnum {

    /**
     * 未审核
     */
    UNAUDITED((byte)0, "未审核"),
    /**
     * 审核通过
     */
    AUDITED_PASS((byte)1, "审核通过"),
	/**
	 * 审核拒绝
	 */
	AUDITED_REFUSE((byte)2, "审核拒绝");

    private Byte code;

    private String message;

    AuditTypeEnum(Byte code, String message) {
        this.code = code;
        this.message = message;
    }

	public Byte getCode() {
		return code;
	}

	public void setCode(Byte code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

