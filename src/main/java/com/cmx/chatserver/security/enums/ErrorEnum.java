package com.cmx.chatserver.security.enums;

import lombok.Getter;

public enum  ErrorEnum {

    //用户相关
    USER_NOT_EXIT(4001, "user not exit", "用户不存在"),
    AUTH_FAIL(4002, "user auth fail", "用户认证失败"),
    LOCKED_USER(4003, "locked user", "用户被锁定"),

    ENUM_NOT_FOND(5008, "enum not fond", "枚举未找到"),

    UNKNOWN_SYSTEM_ERROR(5000, "unknown system error", "未知的系统内部异常");

    @Getter
    private int code;
    @Getter
    private String msgEn;
    @Getter
    private String msgCn;

    ErrorEnum(int code, String msgEn, String msgCn){
        this.code = code;
        this.msgCn = msgCn;
        this.msgEn = msgEn;
    }



}
