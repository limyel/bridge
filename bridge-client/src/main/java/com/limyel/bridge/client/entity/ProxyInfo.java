package com.limyel.bridge.client.entity;

import lombok.Data;

@Data
public class ProxyInfo {

    private String localProxyHost;

    private int localProxyPort;

    private String remoteProxyHost;

    private int remoteProxyPort;

}
