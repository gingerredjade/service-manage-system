package com.smp.admin.model.po.sys;

import com.smp.admin.model.BaseModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 操作日志表
 * 	主要记录系统用户的日常操作信息。
 * 	主要包含编号、用户名、用户操作、请求方法、请求参数、执行时长、IP地址等字段。
 *
 * @author Hongyu Jiang
 * @since  Apr. 24 2020
 */
@Entity
@Table(name = "sys_log")
@EntityListeners(AuditingEntityListener.class)
public class SysLogDO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;


	@Column
	private String userName;	// 用户名

	@Column
	private String operation;	// 用户操作

	@Column
	private String method;		// 请求方法

	@Column
	private String params;		// 请求参数

	@Column
	private Long time;			// 执行时长(毫秒)

	@Column
	private String ip;			// IP地址


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
