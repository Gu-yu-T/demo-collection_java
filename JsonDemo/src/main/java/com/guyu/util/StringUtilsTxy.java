package com.guyu.util;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringUtilsTxy {
    public static void main(String[] args) {
        String str="/Shadow/lite/any/IC676329744341536768/dataTable_1742287679341aPKSOJ/onepiece/";
        int i = str.indexOf("/",1);
        log.info("距离最近的index是：{}",i);
        log.info("字符为：{}",str.charAt(i));

    }
}
