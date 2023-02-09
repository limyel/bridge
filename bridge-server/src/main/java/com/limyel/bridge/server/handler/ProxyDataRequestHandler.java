package com.limyel.bridge.server.handler;

import com.limyel.bridge.protocol.packet.ProxyDataRequestPacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * @author limyel
 * @since 2023-02-08 09:54
 */
public class ProxyDataRequestHandler extends SimpleChannelInboundHandler<ProxyDataRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyDataRequestPacket msg) throws Exception {
        ChannelUtil.getInstance().getChannelGroup().writeAndFlush(msg.getData(), channel -> channel.id().asLongText().equals(msg.getChannelId()));
    }

}
