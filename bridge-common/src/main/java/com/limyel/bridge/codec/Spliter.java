package com.limyel.bridge.codec;

import com.limyel.bridge.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Date;

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
            System.out.println(new Date() + "魔数不匹配，关闭连接");
            // todo 魔数bug
//            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}