package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author limyel
 * @since 2023-02-08 09:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterResponsePacket extends Packet {

    private String uri;

    private String channelId;

    @Override
    public Byte getCommand() {
        return Command.REGISTER_RESPONSE;
    }
}
