package com.xx.xxtest.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 如果你不想修改一个集合，或者期望一个常量集合，那么就可以考虑生成一个 Immutable Collection
 * @author xuxiang
 * 2020/5/14
 */
public class TestImmutableCollections {

    public static void main(String[] args) {
        // 几种创建方式

        // 1. copyOf
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        Set<String> imSet = ImmutableSet.copyOf(set);
        // ImmutableSet.copyOf(ImmutableSet)

        // 2. of
        Set<String> imSet2 = ImmutableSet.of("a", "b", "c");
        Map<String, String> map = ImmutableMap.of("a", "aa", "b", "bb");

        // 3. builder
        ImmutableList<String> list = ImmutableList.<String>builder()
                .addAll(set)
                .add("c")
                .build();
        list.asList(); // return list itself.
    }
}
