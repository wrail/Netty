package com.wrial.netty.socket;
/*
 * @Author  Wrial
 * @Date Created in 0:11 2019/7/29
 * @Description
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

//这个泛型很重要，这取决于到底是那种方式的处理器
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("客户端---"+ctx.channel().remoteAddress()+"------"+msg);
        ctx.writeAndFlush("from server-------" + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //有异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
