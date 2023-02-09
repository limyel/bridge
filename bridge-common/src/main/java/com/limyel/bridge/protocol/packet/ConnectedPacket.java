package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author limyel
 * @since 2023-02-09 14:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConnectedPacket extends Packet {

    private String uri;

    private String channelId;

    @Override
    public Byte getCommand() {
        return Command.CONNECTED;
    }
}
