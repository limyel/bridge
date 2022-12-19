package com.limyel.bridge.client;


import com.limyel.bridge.client.handler.DataHandler;
import com.limyel.bridge.client.handler.HeartBeatTimerHandler;
import com.limyel.bridge.client.handler.RegisterResponseHandler;
import com.limyel.bridge.client.utils.LocalChannelGroup;
import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.codec.Spliter;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Client {

    private final int MAX_RETRY = 5;

    private final String HOST = "192.168.31.32";

    private final int PORT = 9876;

    private final Bootstrap bootstrap;

    public Client() {
        bootstrap = new io.netty.bootstrap.Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IMIdleStateHandler());
                        pipeline.addLast(new Spliter());
                        pipeline.addLast(new PacketDecoder());

                        pipeline.addLast(new RegisterResponseHandler());
                        pipeline.addLast(new DataHandler());
                        pipeline.addLast(new HeartBeatTimerHandler());

                        pipeline.addLast(new PacketEncoder());

                        LocalChannelGroup.INSTANCE.clientChannel = ch;
                    }
                });
    }

    public void start() {
        connect(HOST, PORT, MAX_RETRY);
    }

    private void connect(String host, int port, int retry) {
        try {
            bootstrap.connect(host, port).addListener(future -> {
                if (future.isSuccess()) {
                    return;
                } else if (retry == 0) {
                    System.err.println("重试次数已用完，放弃连接！");
                } else {
                    // 重试次数
                    int order = MAX_RETRY - retry + 1;
                    // 本次重连间隔
                    int delay = 1 << order;
                    System.err.println(new Date() + "：连接失败，第" + order + "次重连...");
                    // 返回 BootstrapConfig
                    bootstrap.config()
                            // 返回线程模型 group
                            .group()
                            // 执行定时任务
                            .schedule(() -> connect(host, port, retry - 1), delay, TimeUnit.SECONDS);
                }
            }).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
