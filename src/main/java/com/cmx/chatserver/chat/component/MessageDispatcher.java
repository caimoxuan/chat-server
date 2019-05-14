package com.cmx.chatserver.chat.component;


import com.cmx.chatserver.chat.component.message.handler.*;
import com.cmx.chatserver.chat.proto.ChatMessageOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Slf4j
@Service
public class MessageDispatcher  {

    @Autowired
    private LoginMessageHandler loginMessageHandler;
    @Autowired
    private UserMessageHandler userMessageHandler;
    @Autowired
    private FileMessageHandler fileMessageHandler;
    @Autowired
    private SystemMessageHandler systemMessageHandler;
    @Autowired
    private DefaultMessageHandler defaultMessageHandler;

    @PostConstruct
    private void init(){
        loginMessageHandler.setNextHandler(userMessageHandler);
        userMessageHandler.setNextHandler(fileMessageHandler);
        fileMessageHandler.setNextHandler(systemMessageHandler);
        systemMessageHandler.setNextHandler(defaultMessageHandler);
    }


    public void dispatchMessage(ChatMessageOuterClass.ChatMessage message){
        loginMessageHandler.handleMessage(message);
    }

}
