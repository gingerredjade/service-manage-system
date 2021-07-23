package com.smp.common.api;

/**
 * API的错误码枚举接口
 * Created by JHy on 2020/4/4.
 */
public interface IErrorCode {

	/**
	 * 获取状态编码
	 * @return 编码
	 */
    Integer getCode();

	/**
	 * 获取提示信息
	 * @return 提示信息
	 */
	String getMessage();
}
