package com.limyel.bridge.client.handler;

import com.limyel.bridge.protocol.packet.InactiveRequestPacket;
import com.limyel.bridge.protocol.packet.ProxyDataRequestPacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.charset.StandardCharsets;

/**
 * @author limyel
 * @since 2023-02-08 09:43
 */
public class ProxyHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    private String remoteChannelId;

    public ProxyHandler(String uri, String remoteChannelId) {
        this.uri = uri;
        this.remoteChannelId = remoteChannelId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        ProxyDataRequestPacket packet = new ProxyDataRequestPacket();
        packet.setData(data);
        packet.setUri(uri);
        packet.setChannelId(remoteChannelId);

        Channel parentChannel = ChannelUtil.getInstance().getParentChannel();
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
        ChannelUtil.getInstance().getChannelMap().remove(remoteChannelId);
        InactiveRequestPacket packet = new InactiveRequestPacket();
        packet.setChannelId(remoteChannelId);
        packet.setUri(uri);
        ChannelUtil.getInstance().getParentChannel().writeAndFlush(packet);
    }
}
