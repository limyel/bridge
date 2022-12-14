package com.limyel.bridge.common.codec;

import com.limyel.bridge.common.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        AbstractPacket packet = PacketCodec.getInstance().decode(byteBuf);
        list.add(packet);
    }
}
