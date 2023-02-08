package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author limyel
 * @since 2023-02-08 09:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProxyDataRequestPacket extends Packet {

    private byte[] data;

    private String uri;

    private String channelId;

    @Override
    public Byte getCommand() {
        return Command.PROXY_DATA_REQUEST;
    }
}
