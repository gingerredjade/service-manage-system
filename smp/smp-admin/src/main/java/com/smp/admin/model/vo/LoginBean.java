package com.smp.admin.model.vo;

/**
 * 登录接口封装对象
 * 	LoginBean是对登录认证信息的简单封装，包含账号密码和验证码信息。
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
public class LoginBean {

	private String account;		// 账户
	private String password;	// 密码
	private String captcha;		// 验证码

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
