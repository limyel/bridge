package com.limyel.bridge.server.handler.proxy;

import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import com.limyel.bridge.server.utils.ProxyChannelGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class DataHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RegisterResponsePacket responsePacket = new RegisterResponsePacket();
        responsePacket.setChannelId(ctx.channel().id().asLongText());

        ProxyChannelGroup.INSTANCE.serverChannel.writeAndFlush(responsePacket);

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;

        ChannelId channelId = ctx.channel().id();
        DataPacket dataPacket = new DataPacket();
        dataPacket.setData(data);
        dataPacket.setChannelId(channelId.asLongText());

        ProxyChannelGroup.INSTANCE.serverChannel.writeAndFlush(dataPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
