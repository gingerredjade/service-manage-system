package com.smp.common;

import com.smp.common.api.AppParams;
import com.smp.common.api.AuxParams;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一的数据处理器接口。
 *
 * @since 1.0.0 2019年09月12日
 * @author <a href="https://gisnci.com">Hongyu Jiang</a>
 */
public interface DataProcFunction<T> {

	/**
	 * 接口名常量
	 */
	public static final String SID = DataProcFunction.class.getName();

	/**
	 * 获取经过处理的请求数据
	 *
	 * @param request		REST请求参数
	 * @param __appParams	用于应用请求控制的参数
	 * @param __auxParams	用于数据处理控制的扩展参数，若为null将被忽略
	 * @return				返回值
	 * @throws Exception	异常信息
	 */
	T processData(HttpServletRequest request, AppParams __appParams, AuxParams __auxParams) throws Exception;


	//T reduce(T value1, T value2) throws Exception;

}
