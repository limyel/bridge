package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.entity.ProxyInfo;
import com.limyel.bridge.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author limyel
 * @since 2023-02-07 22:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterRequestPacket extends Packet {

    private ProxyInfo proxyInfo;

    @Override
    public Byte getCommand() {
        return Command.REGISTER_REQUEST;
    }
}
