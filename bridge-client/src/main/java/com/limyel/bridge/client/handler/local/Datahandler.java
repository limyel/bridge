package com.limyel.bridge.client.handler.local;

import com.limyel.bridge.client.utils.LocalChannelGroup;
import com.limyel.bridge.common.protocol.common.DataPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Datahandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        DataPacket dataPacket = new DataPacket();
        dataPacket.setChannelId(LocalChannelGroup.INSTANCE.channelId);
        dataPacket.setData(data);

        LocalChannelGroup.INSTANCE.clientChannel.writeAndFlush(dataPacket);
    }
}
