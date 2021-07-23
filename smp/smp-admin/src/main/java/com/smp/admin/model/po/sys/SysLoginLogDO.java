package com.smp.admin.model.po.sys;

import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 登录日志表
 * 	主要记录用户登录和退出状态。
 * 	主要包含编号、用户名、登录状态、IP地址等字段。
 * 	可以根据status状态统计在线用户信息。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_login_log")
@EntityListeners(AuditingEntityListener.class)
public class SysLoginLogDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;


	public static final String STATUS_LOGIN = "login";
	public static final String STATUS_LOGOUT = "logout";
	public static final String STATUS_ONLINE = "online";

	@Column
	private String userName;	// 用户名

	@Column
	private String status;		// 登录状态(
								// online:在线,登录初始状态，方便统计在线人数;
								// login:退出登录后将online置为login,;
								// logout:退出登录;)

	@Column
	private String ip;			// IP地址


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
