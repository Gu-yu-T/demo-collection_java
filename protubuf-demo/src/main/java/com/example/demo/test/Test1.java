package com.example.demo.test;

import com.example.demo.proto.VideoInfo;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test1 {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        VideoInfo.VideoFeature videoFeature = VideoInfo.VideoFeature.newBuilder()
                .setAuthorGender(18)
                .setAuthorName("zhangsan")
                .setChannelId(12306l)
                .build();

        log.info("自定义：{}",videoFeature);
        byte[] bytes = videoFeature.toByteArray();
        log.info("变成字节数组：{},大小为：{}",bytes, bytes.length);
        VideoInfo.VideoFeature parse = VideoInfo.VideoFeature.parseFrom(bytes);
        log.info("反序列化后：{}",parse);
    }
}
