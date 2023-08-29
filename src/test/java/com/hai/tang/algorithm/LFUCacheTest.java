package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LFU缓存算法测试类")
public class LFUCacheTest {

    @Test
    @DisplayName("LFU缓存算法测试")
    public void lFUCache() {
        //设置缓存
        LFUCache<String,String> cache = new LFUCache<>(4);
        cache.put("a","a");
        cache.put("b","b");
        cache.put("c","c");
        cache.put("d","d");
        //访问缓存
        cache.get("a");
        cache.get("c");
        cache.get("c");
        System.out.println("缓存"+cache.getValues());
        System.out.println("缓存和访问数量"+cache.getCounts());
        System.out.println("缓存访问数量和访问顺序"+cache.getFrequency());
        System.out.println("getFrequency中将淘汰的缓存位置"+cache.getMin());
        System.out.println("大小"+cache.size);
        System.out.println();
        //添加新缓存e,将淘汰b
        cache.put("e","e");
        System.out.println("缓存"+cache.getValues());
        System.out.println("缓存和访问数量"+cache.getCounts());
        System.out.println("缓存访问数量和访问顺序"+cache.getFrequency());
        System.out.println("getFrequency中将淘汰的缓存位置"+cache.getMin());
        System.out.println("大小"+cache.size);
        System.out.println();

        cache.remove("d");
        System.out.println("缓存"+cache.getValues());
        System.out.println("缓存和访问数量"+cache.getCounts());
        System.out.println("缓存访问数量和访问顺序"+cache.getFrequency());
        System.out.println("getFrequency中将淘汰的缓存位置"+cache.getMin());
        System.out.println("大小"+cache.size);
        System.out.println();

        cache.clear();
        System.out.println("缓存"+cache.getValues());
        System.out.println("缓存和访问数量"+cache.getCounts());
        System.out.println("缓存访问数量和访问顺序"+cache.getFrequency());
        System.out.println("getFrequency中将淘汰的缓存位置"+cache.getMin());
        System.out.println("大小"+cache.size);
        System.out.println();
    }
}
