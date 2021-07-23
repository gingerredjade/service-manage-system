package com.smp.admin.constant.enums;

import lombok.Getter;

/**
 * 第三方平台枚举
 * @author Hongyu Jiang
 * @since  2020/6/20
 */
@Getter
public enum ThirdPartyPlatEnum {

    //第三方平台 1 gitee 2github 3 微信 4 QQ 5 微博 6支付宝
    GITEE(1,"GITEE"),
    GITHUB(2,"GITHUB"),
    WECHAT(3,"微信"),
    QQ(4,"QQ"),
    WEIBO(5,"微博"),
    ALIPAY(6,"支付宝"),
    TAOBAO(7,"淘宝"),
    OTHER(99,"其他"),
    ;


    private Integer code;
    private String msg;

    ThirdPartyPlatEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
