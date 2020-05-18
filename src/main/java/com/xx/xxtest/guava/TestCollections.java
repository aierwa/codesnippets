package com.xx.xxtest.guava;

import com.google.common.collect.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 如果你不想修改一个集合，或者期望一个常量集合，那么就可以考虑生成一个 Immutable Collection
 *
 * @author xuxiang
 * 2020/5/14
 */
public class TestCollections {

    public static void main(String[] args) {
//        testHowToCreateImmutableCollection();
        testNewlyCollection();
    }

    private static void testHowToCreateImmutableCollection() {
        System.out.println("--------- test how to create immutable collection ----------");
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
        // set to list, and get 1 th element.
        ImmutableSet.copyOf(set).asList().get(1);
    }

    private static void testNewlyCollection() {
        System.out.println("--------- test newly collection ----------");
        // Multiset，允许元素重复
        // 像没有顺序约束的 ArrayList， {a,a,b} equals {a,b,a}
        // 也像一个 Map，可以 count 每个元素的次数
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        System.out.println("multiset: " + multiset);
        System.out.println("count b = " + multiset.count("b"));
        System.out.println("elementSet = " + multiset.elementSet());

        // Multimap，包含 Collection 的 map
        ListMultimap<String, Integer> listMultimap = MultimapBuilder
                .hashKeys()
                .arrayListValues()
                .build();
        List<Integer> xxList = listMultimap.get("xx");
        xxList.add(1);
        xxList.add(2);
        System.out.println("xxList: " + xxList);

        // BiMap，可以 key value 互换的 map
        // 所以，BiMap 中 value 也是唯一的，如果 value 已存在，put 会抛错 IllegalArgumentException，可以用 forcePut
        BiMap<String, Integer> userToId = HashBiMap.create();
        userToId.put("xx", 1);
        // throw IllegalArgumentException
//        userToId.put("yy", 1);
        String user = userToId.inverse().get(1);
        System.out.println("id=1,user=" + user);

        // RangeMap，范围内的策略选择
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 100), "1 section");
        rangeMap.put(Range.closed(101, 500), "2 section");
        rangeMap.put(Range.closed(501, 1000), "3 section");
        rangeMap.put(Range.atLeast(1001), "4 section");
        System.out.println(rangeMap.get(1));
        System.out.println(rangeMap.get(100));
        System.out.println(rangeMap.get(101));
        System.out.println(rangeMap.get(1000));
        System.out.println(rangeMap.get(1001));
        System.out.println(rangeMap.get(2000));
    }

    /**
     * 工具类，如 Lists Sets Maps 等
     */
    private static void testUtilities() {
        // 1. 静态构造，可以传初始参数
        List<String> users = Lists.newArrayList();
        List<String> users2 = Lists.newArrayList("xx", "yy");
        List<String> users3 = Lists.newArrayList(new String[]{"xx", "yy"});

        // 2. Iterables

        // 3. Sets
//        Set<String> set1 =
    }
}
