package com.smp.admin.exception;


import com.smp.admin.exception.advice.ResultExceptionAdvice;
import com.smp.admin.util.SpringContextUtil;
import com.smp.common.api.CommonResult;
import com.smp.common.api.CommonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 全局统一异常处理
 * @author 小懒虫
 * @since 2018/8/14
 */
@ControllerAdvice
@Slf4j
public class ResultExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ResultExceptionHandler.class);

    /** 拦截自定义异常 */
    @ExceptionHandler(ResultException.class)
    @ResponseBody
    public CommonResult resultException(ResultException e){
        return CommonResultUtil.failed(e.getCode(), e.getMessage());
    }

    /** 拦截表单验证异常 */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommonResult bindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        return CommonResultUtil.failed(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public CommonResult runtimeException(RuntimeException e) {
        ResultExceptionAdvice resultExceptionAdvice = SpringContextUtil.getBean(ResultExceptionAdvice.class);
        resultExceptionAdvice.runtimeException(e);
		logger.error("【系统异常】", e);
        return CommonResultUtil.failed(500, "未知错误：EX4399");
    }
}
