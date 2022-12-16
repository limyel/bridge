package com.limyel.bridge.common.protocol.request;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestPacket extends AbstractPacket {

    /**
     * 被代理的地址
     */
    private String proxyAddr;

    /**
     * 被代理的端口
     */
    private int proxyPort;

    /**
     * 远程暴露的端口
     */
    private int remotePort;

    @Override
    public Byte getCommand() {
        return CommandConstant.REGISTER_REQUEST;
    }
}
