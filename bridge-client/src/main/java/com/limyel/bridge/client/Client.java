package com.limyel.bridge.client;


import com.limyel.bridge.client.config.ClientConfig;
import com.limyel.bridge.client.handler.DataHandler;
import com.limyel.bridge.client.handler.HeartBeatTimerHandler;
import com.limyel.bridge.client.handler.RegisterResponseHandler;
import com.limyel.bridge.client.net.BridgeClient;
import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.codec.Spliter;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

public class Client {

    public void start(ClientConfig config) {
        BridgeClient client = new BridgeClient();
        ChannelFuture channelFuture = client.connect(config.getServerHost(), config.getServerPort(), new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IMIdleStateHandler());
                pipeline.addLast(new Spliter());
                pipeline.addLast(new PacketDecoder());

                pipeline.addLast(new RegisterResponseHandler());
                pipeline.addLast(new DataHandler());
                pipeline.addLast(new PacketEncoder());
                pipeline.addLast(new HeartBeatTimerHandler());

                ChannelUtil.getInstance().setParentChannel(ch);
            }
        });

    }

}
