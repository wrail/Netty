package com.wrial.netty.socketChat;
/*
 * @Author  Wrial
 * @Date Created in 11:08 2019/7/29
 * @Description
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    //Channel组，里面封装了很多的便捷管理并且线程安全的一组连接的方法
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //服务端收到任何一个客户端都会调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel currentChannel = ctx.channel();
        channelGroup.forEach(ch->{
            if (ch!=currentChannel){
                ch.writeAndFlush(currentChannel.remoteAddress() + "发送消息：" + msg+"\n");
            }else {
                //必须要有换行，因为在服务端转发的时候根据回车换行来进行输出的
               ch.writeAndFlush("我发出：" + msg+"\n");
            }
        });
    }

    //当新建一个连接时会触发
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        channelGroup.writeAndFlush("【全体】--" + ctx.channel().remoteAddress() + "加入聊天室\n");
        channelGroup.add(ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线\n");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"下线\n");
    }

    //当连接断开
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("【全体】--"+channel.remoteAddress()+"离开聊天室\n");
        System.out.println("当前聊天室还剩余"+channelGroup.size()+"人\n");
        //下面这行代码netty会自动调用，如果连接断了就自动从组中移除
        //channelGroup.remove(channel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
