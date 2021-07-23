package com.smp.common.exception;


import com.smp.common.api.CommonResult;
import com.smp.common.api.CommonResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResultUtil.failed(e.getErrorCode());
        }
        return CommonResultUtil.failed(e.getMessage());
    }
}
