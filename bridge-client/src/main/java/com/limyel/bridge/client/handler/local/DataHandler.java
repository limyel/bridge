package com.limyel.bridge.client.handler.local;

import com.limyel.bridge.client.utils.LocalChannelGroup;
import com.limyel.bridge.common.protocol.common.DataPacket;
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

        LocalChannelGroup.INSTANCE.clientChannel.writeAndFlush(dataPacket);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
