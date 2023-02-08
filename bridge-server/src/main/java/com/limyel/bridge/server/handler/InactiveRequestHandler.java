package com.limyel.bridge.server.handler;

import com.limyel.bridge.protocol.packet.InactiveRequestPacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author limyel
 * @since 2023-02-08 13:15
 */
public class InactiveRequestHandler extends SimpleChannelInboundHandler<InactiveRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InactiveRequestPacket msg) throws Exception {
        ChannelUtil.getInstance().getChannelGroup().close(channel -> channel.id().asLongText().equals(msg.getChannelId()));
    }
}
