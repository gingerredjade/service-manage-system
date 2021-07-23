package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * @Title: SortTypeEnum.java
 * @Package: com.smp.admin.constant.enums
 * @Date: 2019/11/27 22:28
 * @Version: v1.0
 * @Description: 枚举类

 * @Author: JiangHy
 * @Copyright: Copyright (c) 2020 GisStudio
 * @Company: www.nci.com
 */
@Getter
public enum SortTypeEnum {
    asc(0,"asc"),
    desc(1,"desc"),
    ;


    private Integer code;
    private String msg;

    SortTypeEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
