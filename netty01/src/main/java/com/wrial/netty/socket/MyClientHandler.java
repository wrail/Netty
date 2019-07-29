package com.wrial.netty.socket;
/*
 * @Author  Wrial
 * @Date Created in 0:27 2019/7/29
 * @Description ClientHandler
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务端"+ctx.channel().remoteAddress()+"-----"+msg);
        System.out.println("client receive"+msg);
        ctx.writeAndFlush("from client:"+ LocalDateTime.now());
    }

    //当连接建立好的使用调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("你好，服务端！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
