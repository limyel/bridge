package com.limyel.bridge.server.handler;

import com.limyel.bridge.protocol.packet.ProxyDataResponsePacket;
import com.limyel.bridge.protocol.packet.RegisterResponsePacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author limyel
 * @since 2023-02-08 09:29
 */
public class ProxyHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    public ProxyHandler(String uri) {
        this.uri = uri;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RegisterResponsePacket packet = new RegisterResponsePacket();
        packet.setUri(uri);
        packet.setChannelId(ctx.channel().id().asLongText());
        ChannelUtil.getInstance().getParentChannel().writeAndFlush(packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;

        ProxyDataResponsePacket packet = new ProxyDataResponsePacket();
        packet.setUri(uri);
        packet.setChannelId(ctx.channel().id().asLongText());
        packet.setData(data);

        System.out.println("ProxyHandler " + ctx.channel().id().asLongText());
        ChannelUtil.getInstance().getParentChannel().writeAndFlush(packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ProxyHandler Inactive " + ctx.channel().id().asLongText());
    }
}
