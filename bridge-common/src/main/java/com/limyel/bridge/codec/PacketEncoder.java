package com.limyel.bridge.codec;

import com.limyel.bridge.protocol.PacketCodec;
import com.limyel.bridge.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author limyel
 * @since 2023-02-07 22:44
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketCodec.getInstance().encode(byteBuf, packet);
    }
}
