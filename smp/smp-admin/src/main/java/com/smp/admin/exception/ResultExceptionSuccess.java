package com.smp.admin.exception;


import com.smp.common.api.ResultCode;
import lombok.Getter;

/**
 * 自定义异常对象{统一异常处理：成功}
 * @author 小懒虫
 * @since 2019/10/17
 */
@Getter
public class ResultExceptionSuccess extends ResultException {

    /**
     * 统一异常处理：抛出默认成功信息
     */
    public ResultExceptionSuccess() {
        super(ResultCode.SUCCESS);
    }

    /**
     * 统一异常处理：抛出成功提示信息
     * @param message 提示信息
     */
    public ResultExceptionSuccess(String message) {
        super(ResultCode.SUCCESS.getCode(), message);
    }
}
