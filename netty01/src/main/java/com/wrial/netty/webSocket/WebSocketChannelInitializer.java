package com.wrial.netty.webSocket;
/*
 * @Author  Wrial
 * @Date Created in 21:59 2019/7/29
 * @Description
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        //将http请求或者响应聚合起来
        pipeline.addLast(new HttpObjectAggregator(8192));
        //WebSocket处理器，负责握手和心跳等等
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义处理器
        pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
