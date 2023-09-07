package com.hai.tang.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LRU缓存算法测试类")
public class LRUCacheTest {

    @Test
    @DisplayName("LRU缓存算法测试")
    public void lRUCache() {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(4);
        //越晚加入lruCache的淘汰优先级越低，淘汰的key优先级是9>7>5>4
        lruCache.put(9, 9);
        lruCache.put(7, 7);
        lruCache.put(5, 5);
        lruCache.put(3, 3);
        System.out.println(lruCache);


        //访问key是9的缓存后，9的优先级变成最低，淘汰优先级是7>5>4>9
        lruCache.get(9);
        //存入10和11后，优先级高的7和5将淘汰
        lruCache.put(10, 10);
        lruCache.put(11, 11);
        System.out.println(lruCache);
    }
}
