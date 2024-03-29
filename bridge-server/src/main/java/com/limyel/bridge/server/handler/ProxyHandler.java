package com.limyel.bridge.server.handler;

import com.limyel.bridge.protocol.packet.ConnectedPacket;
import com.limyel.bridge.protocol.packet.ProxyDataResponsePacket;
import com.limyel.bridge.protocol.packet.RegisterResponsePacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author limyel
 * @since 2023-02-08 09:29
 */
public class ProxyHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    private String channelId;

    public ProxyHandler(String uri, String channelId) {
        this.uri = uri;
        this.channelId = channelId;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "ProxyHandler Active " + ctx.channel().id().asLongText());
        ConnectedPacket packet = new ConnectedPacket();
        packet.setUri(uri);
        packet.setChannelId(ctx.channel().id().asLongText());
        ChannelUtil.getInstance().getChannelMap().get(channelId).writeAndFlush(packet);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;

        ProxyDataResponsePacket packet = new ProxyDataResponsePacket();
        packet.setUri(uri);
        packet.setChannelId(ctx.channel().id().asLongText());
        packet.setData(data);

        System.out.println(new Date() + "ProxyHandler Read " + ctx.channel().id().asLongText());
        ChannelUtil.getInstance().getChannelMap().get(channelId).writeAndFlush(packet);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "ProxyHandler Inactive " + ctx.channel().id().asLongText());
    }
}
