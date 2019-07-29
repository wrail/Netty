package com.wrial.netty.socketChat;
/*
 * @Author  Wrial
 * @Date Created in 0:18 2019/7/29
 * @Description Client
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyChatClient {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    //也可以使用匿名类
                    .handler(new MyChatClientInitializer());

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();

            Channel channel = channelFuture.channel();

            //在这块就不关闭了，就循环接收控制台的输入
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                channel.writeAndFlush(br.readLine() + "\r\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
