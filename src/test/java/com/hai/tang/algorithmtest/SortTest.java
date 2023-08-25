package com.hai.tang.algorithmtest;

import com.hai.tang.algorithm.QuickSort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@DisplayName("快速排序测试类")
public class SortTest {

    @DisplayName("快速排序（升序）")
    @Test
    public void asserCommon() {
        int[] arr = {10, 7, 8, 9, 1, 8, 5};
        QuickSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    @DisplayName("List排序")
    @Test
    public void listSort() {
        List<String> stringList = Arrays.asList("d", "e", "a", "b");
        List<Integer> intList = Arrays.asList(10, 7, 8, 9, 1, 8, 5);
        List<Double> doubleList = Arrays.asList(10.999d, 7.1d, 8.45d, 9.001d, 1.109, 8.559d, 5.001d);
        //升序
        Collections.sort(stringList);
        Collections.sort(intList);
        Collections.sort(doubleList);


        //降序  或者 Collections.sort(stringList,Collections.reverseOrder())
//        stringList.sort(Collections.reverseOrder());
//        intList.sort(Collections.reverseOrder());
//        doubleList.sort(Collections.reverseOrder());

        System.out.println(stringList);
        System.out.println(intList);
        System.out.println(doubleList);
    }

    @DisplayName("Map根据key排序")
    @Test
    public void mapKeySort() {
        //升序
        Map<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(1, "111");
        treeMap.put(8, "888");
        treeMap.put(4, "444");
        treeMap.put(2, "222");
        System.out.println(treeMap);  //输出{1=111, 2=222, 4=444, 8=888}

        //升序
        Map<Integer, String> treeMap1 = new TreeMap<>(Comparator.reverseOrder());
        treeMap1.put(1, "111");
        treeMap1.put(8, "888");
        treeMap1.put(4, "444");
        treeMap1.put(2, "222");
        System.out.println(treeMap1);
    }

    @DisplayName("Map根据value排序")
    @Test
    public void mapValueSort() {
        //升序
        Map<String, Double> budget = new HashMap<>();
        budget.put("普通账户", 1000.00);
        budget.put("信用账户", 2000.00);
        budget.put("期权账户", 500.00);
        budget.put("理财账户", 4000.00);
        Map<String, Double> sorted = budget
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        System.out.println(sorted);

        //降序
        Map<String, Double> budget1 = new HashMap<>();
        budget1.put("普通账户", 1000.00);
        budget1.put("信用账户", 2000.00);
        budget1.put("期权账户", 500.00);
        budget1.put("理财账户", 4000.00);
//jdk8通过stream实现对Map降序排序
        Map<String, Double> sorted1 = budget1
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        System.out.println(sorted1);
    }

    @DisplayName("List<Map>根据map里某一value排序")
    @Test
    public void listMapSort() {
        //升序
        List<Map<String, Object>> resultlist = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("name", "张三");
        map1.put("Score", 86.5);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", "2");
        map2.put("name", "李四");
        map2.put("Score", 90.0);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", "3");
        map3.put("name", "王五");
        map3.put("Score", 70.5);

        resultlist.add(map1);
        resultlist.add(map2);
        resultlist.add(map3);
        resultlist.sort((o1, o2) -> {
            Double one = Double.valueOf(o1.get("Score").toString());
            Double two = Double.valueOf(o2.get("Score").toString());
            return one.compareTo(two);  //one.compareTo(two)为升序，two.compareTo(one)为降序
        });
        System.out.println(resultlist);
    }
}
