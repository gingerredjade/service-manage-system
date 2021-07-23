package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * 菜单类型枚举
 * @author Hongyu Jiang
 * @since  2019/8/14
 */
@Getter
public enum MenuTypeEnum {

    /**
     * 目录类型
     */
    DIRECTORY((byte)0, "目录"),
    /**
     * 菜单类型
     */
    MENU((byte)1, "菜单"),
    /**
     * 按钮类型
     */
    BUTTON((byte)2, "按钮"),

    /**
     * 一级菜单
     * {该枚举已过期，请使用目录类型}
     */
    @Deprecated
    TOP_LEVEL((byte)0, "一级菜单"),
    /**
     * 子级菜单
     * {该枚举已过期，请使用菜单类型}
     */
    @Deprecated
    SUB_LEVEL((byte)1, "子级菜单"),
    /**
     * 按钮类型
     * {该枚举已过期，请使用按钮类型}
     */
    @Deprecated
    NOT_MENU((byte)2, "不是菜单");

    private Byte code;

    private String message;

    MenuTypeEnum(Byte code, String message) {
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

