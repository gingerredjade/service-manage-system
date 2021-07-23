package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * 发布状态枚举
 * @author Hongyu Jiang
 * @since  2020/4/26
 */
@Getter
public enum ReleaseTypeEnum {

    /**
     * 已发布状态
     */
    RELEASED((byte)1, "已发布"),
    /**
     * 未发布状态
     */
    UNRELEASED((byte)0, "未发布");

    private Byte code;

    private String message;

    ReleaseTypeEnum(Byte code, String message) {
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

