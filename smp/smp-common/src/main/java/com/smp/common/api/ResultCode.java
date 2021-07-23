package com.smp.common.api;

/**
 * 枚举了一些常用API操作码
 * Created by JHy on 2020/4/4.
 */
public enum ResultCode implements IErrorCode {

	/**
	 * 通用状态
	 */
	SUCCESS(200, "成功"),
	ERROR(400, "错误"),
	FAILED(500, "操作失败"),
	VALIDATE_FAILED(404, "参数检验失败"),

	/**
	 * 账户问题
	 */
	USER_EXIST(401, "该用户名已经存在"),
	USER_PWD_NULL(402, "密码不能为空"),
	USER_INEQUALITY(403, "两次密码不一致"),
	USER_OLD_PWD_ERROR(404, "原来密码不正确"),
	USER_NAME_PWD_NULL(405, "用户名和密码不能为空"),
	USER_CAPTCHA_ERROR(406, "验证码错误"),

	// 业务错误码
	/**
	 * 角色问题
	 */
	ROLE_EXIST(401, "该角色标识已经存在，不允许重复！"),

	/**
	 * 部门问题
	 */
	DEPT_EXIST_USER(401, "部门存在用户，无法删除"),
	DEPT_EXIST_USEDCHILD(401, "机构包含已被占用子机构，无法删除"),
	DEPT_USED(401, "机构已被占用，禁止删除"),

	/**
	 * 字典问题
	 */
	DICT_EXIST(401, "该字典标识已经存在，不允许重复！"),

	/**
	 * 非法操作
	 */
	STATUS_ERROR(401, "非法操作：状态有误"),

	/**
	 * 权限问题
	 */
	NO_PERMISSIONS(401, "权限不足！"),
	NO_ADMIN_AUTH(500, "不允许操作超级管理员"),
	NO_ADMIN_STATUS(501, "不能修改超级管理员状态"),
	NO_ADMINROLE_AUTH(500, "不允许操作管理员角色"),
	NO_ADMINROLE_STATUS(501, "不能修改管理员角色状态"),
	NO_ADMINROLEMENU_STATUS(501, "超级管理员拥有所有菜单权限，不允许修改"),
	UNAUTHORIZED(402, "暂未登录或token已经过期"),

	/**
	 * 菜单问题
	 */
	MENU_EXIST_USEDCHILD(401, "包含已被占用子菜单，无法删除"),
	MENU_USED(401, "菜单已被占用，禁止删除"),

	;

	private Integer code;
	private String message;

	private ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
