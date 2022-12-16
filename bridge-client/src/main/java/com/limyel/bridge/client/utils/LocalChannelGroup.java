package com.limyel.bridge.client.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;

public class LocalChannelGroup {

    public static LocalChannelGroup INSTANCE = new LocalChannelGroup();

    public ChannelGroup channelGroup;

    public Channel clientChannel;

    public Map<String, Channel> channelMap = new HashMap<>();

    public Channel localChannel;

    public String channelId;

    private LocalChannelGroup() {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

}
