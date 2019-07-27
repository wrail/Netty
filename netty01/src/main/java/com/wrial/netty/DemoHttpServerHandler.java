package com.wrial.netty;
/*
 * @Author  Wrial
 * @Date Created in 11:51 2019/7/27
 * @Description 自定义处理器
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class DemoHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /*
    读取客户端请求，并进行响应
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress());
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;

            System.out.println("请求方法名：" + httpRequest.method().name());

            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse
                    (HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            //写到缓冲区并Flash
            ctx.writeAndFlush(response);
            ctx.channel().close();
        }
    }
}
