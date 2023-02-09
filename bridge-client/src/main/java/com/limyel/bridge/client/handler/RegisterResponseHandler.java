package com.limyel.bridge.client.handler;

import com.limyel.bridge.protocol.packet.RegisterResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author limyel
 * @since 2023-02-09 14:41
 */
public class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponsePacket msg) throws Exception {
        System.out.println(msg.getMsg());
        if (!msg.isSuccess()) {
            ctx.close();
        }
    }
}
