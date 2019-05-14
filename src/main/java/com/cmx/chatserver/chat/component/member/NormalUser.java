package com.cmx.chatserver.chat.component.member;

import lombok.Data;

@Data
public class NormalUser extends BaseUser {

    /**
     * 接入token
     */
    private String token;

    /**
     * 是否接入状态
     */
    private boolean isActive;

}
