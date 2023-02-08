package com.limyel.bridge.client.handler;

import com.limyel.bridge.protocol.packet.ProxyDataResponsePacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * @author limyel
 * @since 2023-02-08 09:59
 */
public class ProxyDataResponseHandler extends SimpleChannelInboundHandler<ProxyDataResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProxyDataResponsePacket msg) throws Exception {
        Map<String, Channel> channelMap = ChannelUtil.getInstance().getChannelMap();
        Channel channel = ChannelUtil.getInstance().getChannelMap().get(msg.getChannelId());
        channel.writeAndFlush(msg.getData());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
