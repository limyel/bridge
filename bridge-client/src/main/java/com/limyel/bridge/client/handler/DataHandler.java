package com.limyel.bridge.client.handler;

import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DataHandler extends SimpleChannelInboundHandler<DataPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket dataPacket) throws Exception {
        System.out.println(new String(dataPacket.getData(), StandardCharsets.UTF_8));
        Map<String, Channel> channelMap = ChannelUtil.getInstance().getChannelMap();
        ChannelUtil.getInstance().getChannelMap().get(dataPacket.getChannelId()).writeAndFlush(dataPacket.getData());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
