package com.cmx.chatserver.chat.component.message.handler;


import com.cmx.chatserver.chat.component.message.router.MessageRouter;
import com.cmx.chatserver.chat.proto.ChatMessageOuterClass;
import com.cmx.chatserver.chat.repository.MongoRepository;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cmx
 * @date 2019/3/1
 */
@Slf4j
@Component
public abstract class ProtoBufMessageHandler {


    @Setter
    ProtoBufMessageHandler nextHandler;

    @Autowired
    MessageRouter messageRouter;

    @Autowired
    MongoRepository mongoRepository;

    /**
     * webSocket消息处理
     * @param chatMessage 消息内容
     */
    abstract void handleMessage(ChatMessageOuterClass.ChatMessage chatMessage);


    void saveMessage(ChatMessageOuterClass.ChatMessage chatMessage) {
        //将消息中的文件信息去除，不要保存二进制数据
        String jsonMessage = JsonFormat.printToString(chatMessage.toBuilder().setFileMessage(chatMessage.toBuilder().getFileMessage().toBuilder().clearFileContent()).build());
        System.out.println(jsonMessage);
        try {
            mongoRepository.insert(jsonMessage, "chat_log");
        }catch(Exception e){
            log.info("save message to mongo get error : {}", e);
        }
    }

}
