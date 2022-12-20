package com.limyel.bridge.server.handler.proxy;

import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

public class DataHandler extends ChannelInboundHandlerAdapter {

    // todo 激活，拆分？
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(this + " " + ctx.channel().id().asLongText());
        String channelId = ctx.channel().id().asLongText();
        RegisterResponsePacket responsePacket = new RegisterResponsePacket();
        Map<String, String> map = ChannelUtil.getInstance().getMap();
        String s = map.get(channelId);
        responsePacket.setHostPort(ChannelUtil.getInstance().getMap().get(channelId));
        responsePacket.setChannelId(channelId);

        ChannelUtil.getInstance().getParentChannel().writeAndFlush(responsePacket);

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;

        System.out.println(this + " " + ctx.channel().id().asLongText());
        ChannelId channelId = ctx.channel().id();
        DataPacket dataPacket = new DataPacket();
        dataPacket.setData(data);
        dataPacket.setChannelId(channelId.asLongText());

        ChannelUtil.getInstance().getParentChannel().writeAndFlush(dataPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
