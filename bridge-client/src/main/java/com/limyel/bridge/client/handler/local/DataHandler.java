package com.limyel.bridge.client.handler.local;

import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.protocol.request.DisconnectRequestPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class DataHandler extends ChannelInboundHandlerAdapter {

    private String channelId;

    public DataHandler(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;

        DataPacket dataPacket = new DataPacket();
        dataPacket.setChannelId(this.channelId);
        dataPacket.setData(data);

        ChannelUtil.getInstance().getParentChannel().writeAndFlush(dataPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ChannelUtil.getInstance().getChannelGroup().close(channel -> channel.id().asLongText().equals(ctx.channel().id().asLongText()));
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtil.getInstance().getChannelMap().remove(channelId);

        DisconnectRequestPacket requestPacket = new DisconnectRequestPacket();
        requestPacket.setChannelId(channelId);
        ChannelUtil.getInstance().getParentChannel().writeAndFlush(requestPacket);
    }
}
