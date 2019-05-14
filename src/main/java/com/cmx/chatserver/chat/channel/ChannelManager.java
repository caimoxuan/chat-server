package com.cmx.chatserver.chat.channel;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author cmx
 */
public class ChannelManager {

    private static final ThreadLocal<ChannelHandlerContext> localChannel = new ThreadLocal<>();


    public static ChannelHandlerContext getChannel(){
        return localChannel.get();
    }


    public static void setChannel(ChannelHandlerContext channel){
        localChannel.set(channel);
    }

    public static void remove(){
        localChannel.remove();
    }

}
