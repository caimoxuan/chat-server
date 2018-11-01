package com.cmx.chatserver.chat.component.message;

import com.cmx.chatserver.chat.constant.DefaultConstant;
import lombok.Data;

/**
 * @author: cmx
 */
@Data
public class Message {

    /**
     * 消息的id 唯一
     */
    protected String msgId;

    /**
     * 消息的内容
     */
    protected String msgContext;

    /**
     * 消息的时间戳
     */
    protected Long timeStamp;

    /**
     * 发送消息的用户信息
     */
    protected Long sendUser;

    /**
     * 接收消息的用户id
     */
    protected Long receiveUser;

    /**
     * 消息所在的房间
     */
    protected String roomId = DefaultConstant.DEFAULT_HALL_ID;

    /**
     * 消息类型
     */
    protected String messageType;
    /**
     * 消息应该显示的方向
     */
    protected String dir;

}
