package com.limyel.bridge.server.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ProxyChannelGroup {

    public static ProxyChannelGroup INSTANCE = new ProxyChannelGroup();

    public ChannelGroup channelGroup;

    public Channel serverChannel;

    public Channel proxyChannel;

    private ProxyChannelGroup() {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

}
