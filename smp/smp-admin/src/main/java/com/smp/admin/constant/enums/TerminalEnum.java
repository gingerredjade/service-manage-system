package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * 终端类型枚举
 * @author Hongyu Jiang
 * @since  2020/7/21
 */
@Getter
public enum TerminalEnum {

	/**
	 * 菜单类型
	 */
	GENERAL((byte)1, "通用"),
	/**
	 * 按钮类型
	 */
	WEB((byte)2, "Web端"),
	/**
	 * 目录类型
	 */
	DESKTOP((byte)3, "桌面端");

	private Byte code;

	private String message;

	TerminalEnum(Byte code, String message) {
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
