package com.limyel.bridge.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author limyel
 * @since 2023-02-09 10:39
 */
public abstract class BridgeConfig {

    protected Properties properties = new Properties();

    protected void load(String path) {
        try {
            properties.load(Files.newInputStream(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件异常或不存在");
        }
    }

}
