package com.cmx.chatserver.chat.component.message;

import com.cmx.chatserver.chat.constant.DefaultConstant;
import lombok.Data;

/**
 * @author: cmx
 */
@Data
public class Message {

    protected String msgId;

    protected String msgContext;

    protected Long timeStamp;

    protected Long sendUser;

    protected Long receiveUser;

    protected String roomId = DefaultConstant.DEFAULT_HALL_ID;

    protected String messageType;

}
