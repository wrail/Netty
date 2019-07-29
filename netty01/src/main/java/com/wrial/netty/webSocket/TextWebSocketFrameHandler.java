package com.wrial.netty.webSocket;
/*
 * @Author  Wrial
 * @Date Created in 22:13 2019/7/29
 * @Description
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

//不仅仅是字符串能解决的，因为还要和协议进行协调
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrameHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrameHandler msg) throws Exception {
        System.out.println("收到消息"+msg);
        //直接写字符串是发送不了的
        ctx.channel().writeAndFlush(new TextWebSocketFrame("当前服务器时间" + LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded"+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved"+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception");
        ctx.close();
    }
}
