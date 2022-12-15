package com.limyel.bridge.common.protocol.request;

import lombok.Data;

@Data
public class RegisterItem {

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

}
