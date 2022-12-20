package com.limyel.bridge.server.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;

// todo 优化、封装
@Data
public class ChannelUtil {

    private static ChannelUtil INSTANCE;

    private ChannelUtil() {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public static ChannelUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ChannelUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ChannelUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ChannelGroup channelGroup;

    private Channel serverChannel;

    private Channel proxyChannel;

}
