package com.guyu.caffeine;



import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaffeineDemo {
    public static void main(String[] args) throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();

        cache.put("1","hello");
        cache.put("2","world");

        Object value1 = cache.getIfPresent("1");
        log.info("key为1在缓存中的值：{}",value1);

        Object value3 = cache.getIfPresent("3");
        log.info("key为3在缓存中的值：{}",value3);

        Thread.sleep(1000*60);

        Object value4 = cache.getIfPresent("1");
        log.info("系统沉睡1分钟后，key为1在缓存中的值：{}",value4);

    }
}
