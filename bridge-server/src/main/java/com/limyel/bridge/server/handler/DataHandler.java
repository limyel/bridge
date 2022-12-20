package com.limyel.bridge.server.handler;

import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DataHandler extends SimpleChannelInboundHandler<DataPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataPacket dataPacket) throws Exception {

        ChannelUtil.getInstance().getChannelGroup().writeAndFlush(dataPacket.getData(), channel -> channel.id().asLongText().equals(dataPacket.getChannelId()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
