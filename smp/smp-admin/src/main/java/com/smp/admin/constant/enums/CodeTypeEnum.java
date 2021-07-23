package com.smp.admin.constant.enums;

import lombok.Getter;

@Getter
public enum CodeTypeEnum {
	// 类型 0JAVA HTML 2其他'
    JAVA(0,"JAVA"),
    HTML(1,"HTML"),
    OTHER(2,"其他"),
    ;


    private Integer code;
    private String msg;

    CodeTypeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
