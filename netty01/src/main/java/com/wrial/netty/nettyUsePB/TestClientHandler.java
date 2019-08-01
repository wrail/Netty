package com.wrial.netty.nettyUsePB;
/*
 * @Author  Wrial
 * @Date Created in 20:50 2019/7/31
 * @Description
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //使用随机数来模仿
        int randomInt = new Random().nextInt(3);

        MyDataInfo.MyMessage myMessage = null;

        if (randomInt == 0) {

            myMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(MyDataInfo.Person.newBuilder()
                            .setName("Wrial")
                            .setAge(20)
                            .setAddress("China")
                            .build()).build();
        } else if (randomInt == 1) {
            myMessage = MyDataInfo.MyMessage.newBuilder().
                    setDataType(MyDataInfo.MyMessage.DataType.DogType).
                    setDog(MyDataInfo.Dog.newBuilder().
                            setName("一只狗").setAge(2).
                            build()).
                    build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder().
                    setDataType(MyDataInfo.MyMessage.DataType.CatType).
                    setCat(MyDataInfo.Cat.newBuilder().
                            setName("一只猫").setCity("上海").
                            build()).
                    build();
        }
        ctx.channel().writeAndFlush(myMessage);

    }
}
