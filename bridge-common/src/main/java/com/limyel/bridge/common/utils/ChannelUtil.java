package com.limyel.bridge.common.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// todo 优化、封装
@Data
public class ChannelUtil {

    private static volatile ChannelUtil INSTANCE = new ChannelUtil();

    private ChannelUtil() {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        channelMap = new HashMap<>();
        map = new HashMap<>();
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

    private Map<String, Channel> channelMap;

    private Map<String, String> map;

    private Channel parentChannel;

}
