package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author limyel
 * @since 2023-02-08 09:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProxyDataResponsePacket extends Packet {

    private String uri;

    private String channelId;

    private byte[] data;

    @Override
    public Byte getCommand() {
        return Command.PROXY_DATA_RESPONSE;
    }
}
