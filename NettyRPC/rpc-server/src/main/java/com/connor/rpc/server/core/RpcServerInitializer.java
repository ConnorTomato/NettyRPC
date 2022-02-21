package com.connor.rpc.server.core;

import com.connor.rpc.codec.*;
import com.connor.rpc.serializer.Serializer;
import com.connor.rpc.serializer.kryo.KryoSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 将心跳监控、 拆包器（拆解自定义协议获取内容）、解码器、编码器、Requset处理器加入pipeline
 * @Author: connor
 */
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {
    private Map<String, Object> handlerMap;
    private ThreadPoolExecutor threadPoolExecutor;

    public RpcServerInitializer(Map<String, Object> handlerMap, ThreadPoolExecutor threadPoolExecutor) {
        this.handlerMap = handlerMap;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void initChannel(SocketChannel channel) throws Exception {
//        Serializer serializer = ProtostuffSerializer.class.newInstance();
//        Serializer serializer = HessianSerializer.class.newInstance();
        Serializer serializer = KryoSerializer.class.newInstance();
        ChannelPipeline cp = channel.pipeline();
        cp.addLast(new IdleStateHandler(0, 0, Beat.BEAT_TIMEOUT, TimeUnit.SECONDS));
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        cp.addLast(new RpcDecoder(RpcRequest.class, serializer));
        cp.addLast(new RpcEncoder(RpcResponse.class, serializer));
        cp.addLast(new RpcServerHandler(handlerMap, threadPoolExecutor));
    }
}
