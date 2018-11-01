package com.cmx.chatserver.chat.component.message.observer;


import com.cmx.chatserver.chat.component.message.event.MessageEvent;

import java.util.EventListener;

/**
 * @author cmx
 * 消息监听器
 */
public interface MessageListener extends EventListener {
    /**
     * 消息处理
     * @param messageEvent
     */
    void onMessage(MessageEvent messageEvent);

}
