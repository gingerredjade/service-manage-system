package com.smp.common.api;

import lombok.Data;

/**
 * 通用返回对象(最外层对象)
 * Created by JHy on 2010/4/4.
 */
@Data
//@ApiModel("响应结果")
public class CommonResult<T> {
    private long code;			// 返回结果状态值
    private String message;		// 返回提示信息
    private T data;				// 返回结果体

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
