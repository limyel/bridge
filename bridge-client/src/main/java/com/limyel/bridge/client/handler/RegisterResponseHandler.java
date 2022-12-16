package com.limyel.bridge.client.handler;

import com.limyel.bridge.common.protocol.request.ConnectRequestPacket;
import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.omg.CORBA.portable.ResponseHandler;

public class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RegisterRequestPacket requestPacket = new RegisterRequestPacket("192.168.31.98", 8000, 7001);
        ctx.channel().writeAndFlush(requestPacket);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponsePacket responsePacket) throws Exception {

    }
}
