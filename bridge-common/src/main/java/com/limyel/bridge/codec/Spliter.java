package com.limyel.bridge.codec;

import com.limyel.bridge.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author limyel
 * @since 2023-02-07 17:08
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 5;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getShort(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            System.out.println("魔数不匹配，关闭连接");
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}