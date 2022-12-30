package com.limyel.bridge.common.codec;

import com.limyel.bridge.common.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket packet, ByteBuf byteBuf) throws Exception {
        PacketCodec.getInstance().encode(byteBuf, packet);
    }
}
