package com.limyel.bridge.client.handler;

import com.limyel.bridge.protocol.packet.InactiveRequestPacket;
import com.limyel.bridge.protocol.packet.ProxyDataRequestPacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.*;

import java.util.Date;

/**
 * @author limyel
 * @since 2023-02-08 09:43
 */
public class ProxyHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    private String remoteChannelId;

    private String parentChannelId;

    public ProxyHandler(String uri, String remoteChannelId, String parentChannelId) {
        this.uri = uri;
        this.remoteChannelId = remoteChannelId;
        this.parentChannelId = parentChannelId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        ProxyDataRequestPacket packet = new ProxyDataRequestPacket();
        packet.setData(data);
        packet.setUri(uri);
        packet.setChannelId(remoteChannelId);

        System.out.println(new Date() + "ProxyHandler " + ctx.channel().id().asLongText());
        ChannelUtil.getInstance().getParentChannelMap().get(parentChannelId).writeAndFlush(packet);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + "ProxyHandler Inactive " + ctx.channel().id().asLongText());
        ChannelUtil.getInstance().getChannelMap().remove(remoteChannelId);
        InactiveRequestPacket packet = new InactiveRequestPacket();
        packet.setChannelId(remoteChannelId);
        packet.setUri(uri);
        ChannelUtil.getInstance().getParentChannelMap().get(parentChannelId).writeAndFlush(packet);
    }
}
