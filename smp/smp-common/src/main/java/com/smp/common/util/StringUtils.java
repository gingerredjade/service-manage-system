package com.smp.common.util;

/**
 * 字符串工具类
 * @author Hongyu Jiang
 * @since Apr 27, 2021
 */
public class StringUtils {

	/**
	 * 判空操作
	 * @param value 字符串
	 * @return boolean
	 */
	public static boolean isBlank(String value) {
		return value == null || "".equals(value) || "null".equals(value) || "undefined".equals(value);
	}

}
