package com.smp.common.api;

import java.util.ArrayList;
import java.util.List;

/**
 * POST请求参数体
 * @author Hongyu Jiang
 * @since Nov 19, 2020
 */
public class UniversalRequestVO {

	/**
	 * 查询参数
	 */
	private List<Param> params = new ArrayList<>();
	
	public void setParams(List<Param> params) {
		this.params = params;
	}

	/**
	 * 查询参数对象
	 * @param name 参数名称
	 * @return 返回值
	 */
	public Param getParam(String name) {
		for(Param param:this.params) {
			if(name != null && name.equals(param.getName())) {
				return param;
			}
		}
		return null;
	}
	/**
	 * 查询参数值
	 * @param name 参数名称
	 * @return 返回值
	 */
	public String getParamValue(String name) {
		Param param = getParam(name);
		if(param != null) {
			return param.getValue();
		}
		return null;
	}

}
