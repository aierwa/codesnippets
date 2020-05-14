package com.xx.xxtest.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author xuxiang
 * 2020/5/14
 */
public class TestCache {
    public static void main(String[] args) {
        Cache<String, Long> id2time = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .recordStats()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();

        for (int i = 0; i < 10; i++) {
            id2time.put("" + i, System.nanoTime());
        }

        System.out.println(id2time.getIfPresent("1"));
        System.out.println(id2time.getIfPresent("100"));

        System.out.println(id2time.stats().toString());
    }
}
