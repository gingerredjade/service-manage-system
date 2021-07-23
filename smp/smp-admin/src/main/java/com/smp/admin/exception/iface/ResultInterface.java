package com.smp.admin.exception.iface;

/**
 * 结果枚举接口
 * @author Hongyu Jiang
 * @since 2019/2/13
 */
public interface ResultInterface {

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
