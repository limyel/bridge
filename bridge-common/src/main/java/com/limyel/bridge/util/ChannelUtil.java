package com.limyel.bridge.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limyel
 * @since 2023-02-08 09:34
 */
@Data
public class ChannelUtil {

    private static volatile ChannelUtil INSTANCE = new ChannelUtil();

    private ChannelUtil() {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        channelMap = new HashMap<>();
        parentChannelMap = new HashMap<>();
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

    private Map<String, Channel> parentChannelMap;

    private Map<String, String> map;

}
