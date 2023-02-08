package com.limyel.bridge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author limyel
 * @since 2023-02-07 22:24
 */
@Data
public class ProxyInfo {

    /**
     * 被代理的地址
     */
    private String proxyHost;

    /**
     * 被代理的端口
     */
    private int proxyPort;

    /**
     * 远程暴露的端口
     */
    private int remotePort;

    @JsonIgnore
    public String getUri() {
        return proxyHost + ":" + proxyPort;
    }

}
