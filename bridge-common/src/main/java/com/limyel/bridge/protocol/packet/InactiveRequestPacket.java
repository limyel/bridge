package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author limyel
 * @since 2023-02-08 12:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InactiveRequestPacket extends Packet {

    private String uri;

    private String channelId;

    @Override
    public Byte getCommand() {
        return Command.INACTIVE_REQUEST;
    }
}
