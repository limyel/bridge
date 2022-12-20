package com.limyel.bridge.client.handler;

import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DataHandler extends SimpleChannelInboundHandler<DataPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket dataPacket) throws Exception {
        ChannelUtil.getInstance().getChannelMap().get(dataPacket.getChannelId()).writeAndFlush(dataPacket.getData());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
