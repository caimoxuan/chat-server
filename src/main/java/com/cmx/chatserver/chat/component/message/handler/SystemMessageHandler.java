package com.cmx.chatserver.chat.component.message.handler;

import com.cmx.chatserver.chat.enums.MessageType;
import com.cmx.chatserver.chat.proto.ChatMessageOuterClass;
import org.springframework.stereotype.Component;

/**
 * @author cmx
 * @date 2019/3/1
 */
@Component
public class SystemMessageHandler extends ProtoBufMessageHandler {

    @Override
    public void handleMessage(ChatMessageOuterClass.ChatMessage chatMessage) {
        if(MessageType.SYSTEM.getCode() == chatMessage.getMessageType()) {
            System.out.println("handler system message");
        } else {
            nextHandler.handleMessage(chatMessage);
        }
    }
}
