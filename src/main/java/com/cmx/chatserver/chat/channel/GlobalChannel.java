package com.cmx.chatserver.chat.channel;


import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class GlobalChannel {

    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

}
