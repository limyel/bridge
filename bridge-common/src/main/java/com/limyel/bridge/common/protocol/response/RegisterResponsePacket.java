package com.limyel.bridge.common.protocol.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterResponsePacket extends AbstractPacket {

    private String localProxyHost;

    private int localProxyPort;

    private String channelId;

    @JsonIgnore
    public void setHostPort(String uri) {
        String[] split = uri.split(":");
        if (split.length != 2) {
            // todo 异常
        }
        this.localProxyHost = split[0];
        this.localProxyPort = Integer.parseInt(split[1]);
    }

    @Override
    public Byte getCommand() {
        return CommandConstant.REGISTER_RESPONSE;
    }
}
