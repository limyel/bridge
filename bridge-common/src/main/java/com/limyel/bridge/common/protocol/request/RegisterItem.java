package com.limyel.bridge.common.protocol.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
