package com.wrial.netty.PB;
/*
 * @Author  Wrial
 * @Date Created in 22:23 2019/7/30
 * @Description
 */

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        MyDataInfo.Student student = MyDataInfo.Student.newBuilder()
                .setName("Wrial")
                .setAge(21)
                .setAddress("西安")
                .build();
        byte[] studentToByteArray = student.toByteArray();

        MyDataInfo.Student student1 = MyDataInfo.Student.parseFrom(studentToByteArray);
        System.out.println(student1);

        System.out.println(student.getName()+" "+student.getAge()+" "+student.getAddress());
        System.out.println(student1.getName()+" "+student1.getAge()+" "+student1.getAddress());
    }

}
