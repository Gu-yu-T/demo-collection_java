package com.guyu.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guyu.pojo.Human;
import com.guyu.pojo.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FastJsonDemo {
    public static void main(String[] args) {
        User user = new User(1,"zhansan\\",18);
        String s = JSON.toJSONString(user);
        Human human = new Human(user,s);
        String s1 = JSON.toJSONString(human);
        log.info(s1);

        Human human1 = JSON.parseObject(s1, Human.class);
        log.info("human1:{}",human1);
        String userJson = human1.getUserJson();
        User user1 = JSON.parseObject(userJson, User.class);
        log.info("user1:{}",user1);
        String s2 = "{\\\"age\":18,\"id\":1,\"name\":\"zhansan\\\\\"}";
        User user2 = JSON.parseObject(s2, User.class);
        log.info("user2:{}",user2);

    }
}
