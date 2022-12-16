package com.limyel.bridge.common.protocol.request;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConnectRequestPacket extends AbstractPacket {

    private String channelId;

    @Override
    public Byte getCommand() {
        return CommandConstant.CONNECT_REQUEST;
    }
}
