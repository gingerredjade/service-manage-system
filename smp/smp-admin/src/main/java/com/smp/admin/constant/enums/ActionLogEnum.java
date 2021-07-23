package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * 操作动作枚举
 * @author Hongyu Jiang
 * @since 2020/4/27
 */
@Getter
public enum ActionLogEnum {

    /**
     * 业务日志行为
     */
    BUSINESS((byte) 1, "业务"),
    /**
     * 用户登录日志行为
     */
    LOGIN((byte) 2, "登录"),
    /**
     * 系统日志行为（报错信息）
     */
    SYSTEM((byte) 3, "系统");

    private Byte code;

    private String message;

    ActionLogEnum(Byte code, String message) {
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
