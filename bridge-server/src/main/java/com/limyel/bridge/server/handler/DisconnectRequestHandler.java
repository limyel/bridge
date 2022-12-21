package com.limyel.bridge.server.handler;

import com.limyel.bridge.common.protocol.request.DisconnectRequestPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DisconnectRequestHandler extends SimpleChannelInboundHandler<DisconnectRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DisconnectRequestPacket requestPacket) throws Exception {
        ChannelUtil.getInstance().getChannelGroup().close(channel -> channel.id().asLongText().equals(requestPacket.getChannelId()));
    }
}
