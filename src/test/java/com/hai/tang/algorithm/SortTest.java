package com.hai.tang.algorithm;

import com.hai.tang.model.Student;
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
    public void quickSort() {
        int[] arr = {10, 7, 8, 9, 1, 8, 5};
        QuickSort.sort(arr);
        System.out.println(Arrays.toString(arr));
    }


    @DisplayName("数组排序")
    @Test
    public void arraySort() {
        int[] arr = {10, 7, 8, 9, 1, 8, 5};
        // 升序
        Arrays.sort(arr);

        //降序
        // arr = IntStream.of(arr).boxed().sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();

        //如果 arr是 Integer 包装类型，即 arr = {10, 7, 8, 9, 1, 8, 5} 可先升序再反转来降序排序
        //Arrays.sort(arr);
        //Arrays.sort(arr, Collections.reverseOrder());
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
        //stringList.sort(Collections.reverseOrder());
        //intList.sort(Collections.reverseOrder());
        //doubleList.sort(Collections.reverseOrder());

        System.out.println(stringList);
        System.out.println(intList);
        System.out.println(doubleList);
    }


    @DisplayName("List字符串自定义排序")
    @Test
    public void listStringSort() {
        //待排序的list
        List<String> inputList = new ArrayList<>(Arrays.asList("salesgroup", "asstacttype", "asstact", "salesman", "currency", "basecurrency", "org", "billtypeid"));

        // 定义排序顺序
        List<String> sortOrder = Arrays.asList("org", "asstacttype", "asstact", "salesorg", "salesdept", "salesgroup", "salesman", "billtypeid", "currency", "basecurrency");

        // 创建一个自定义的Comparator,按照 sortOrder 顺序排序
        Comparator<String> customComparator = (s1, s2) -> {
            int s1Index = sortOrder.indexOf(s1);
            int s2Index = sortOrder.indexOf(s2);
            if (s1Index == -1 || s2Index == -1) {
                return s1.compareTo(s2); // 如果某个元素不在排序顺序中，就按字典序排序
            }
            return s1Index - s2Index; // 按排序顺序排序
        };

        // 使用自定义的Comparator对inputList进行排序
        inputList.sort(customComparator);

        //输出：[org, asstacttype, asstact, salesgroup, salesman, billtypeid, currency, basecurrency]
        System.out.println(inputList);
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

        //降序
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

    @DisplayName("List<Object>根据Object的某一属性对List进行排序")
    @Test
    public void listObjectSort() {
        List<Student> list = new ArrayList<>();
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        student1.setName("张三");
        student1.setScore(92);
        student2.setName("李四");
        student2.setScore(86);
        student3.setName("王五");
        student3.setScore(100);
        list.add(student1);
        list.add(student2);
        list.add(student3);

        System.out.println("排序前：");
        for (Student value : list) {
            System.out.println(value.getName() + "：" + value.getScore());
        }
        System.out.println("--------");
        System.out.println("排序后：");
        //升序
        list.sort(Comparator.comparingInt(Student::getScore));
        //降序
        //list.sort((o1, o2) -> o2.getScore() - o1.getScore());
        for (Student student : list) {
            System.out.println(student.getName() + "：" + student.getScore());
        }
    }

    @DisplayName("复杂排序示例")
    @Test
    public void complexSortDemo() {
        List<Map<String, List<Map<String, Object>>>> tagsList = new ArrayList<>();

        List<Map<String, Object>> resultlist1 = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap1 = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("name", "张一");
        map1.put("Score", 7L);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", "2");
        map2.put("name", "李一");
        map2.put("Score", 100L);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", "3");
        map3.put("name", "王一");
        map3.put("Score", 86L);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("id", "4");
        map4.put("name", "赵一");
        map4.put("Score", 0L);
        resultlist1.add(map1);
        resultlist1.add(map2);
        resultlist1.add(map3);
        resultlist1.add(map4);
        listMap1.put("1", resultlist1);
        tagsList.add(listMap1);

        List<Map<String, Object>> resultlist2 = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap2 = new HashMap<>();
        Map<String, Object> map2_1 = new HashMap<>();
        map2_1.put("id", "1");
        map2_1.put("name", "张二");
        map2_1.put("Score", 709L);
        Map<String, Object> map2_2 = new HashMap<>();
        map2_2.put("id", "2");
        map2_2.put("name", "李二");
        map2_2.put("Score", 0L);
        Map<String, Object> map2_3 = new HashMap<>();
        map2_3.put("id", "3");
        map2_3.put("name", "王二");
        map2_3.put("Score", 456L);
        Map<String, Object> map3_4 = new HashMap<>();
        map3_4.put("id", "4");
        map3_4.put("name", "赵二");
        map3_4.put("Score", 0L);
        resultlist2.add(map2_1);
        resultlist2.add(map2_2);
        resultlist2.add(map2_3);
        resultlist2.add(map3_4);
        listMap2.put("2", resultlist2);
        tagsList.add(listMap2);

        List<Map<String, Object>> resultlist3 = new ArrayList<>();
        Map<String, List<Map<String, Object>>> listMap3 = new HashMap<>();
        Map<String, Object> map3_1 = new HashMap<>();
        map3_1.put("id", "1");
        map3_1.put("name", "张三");
        map3_1.put("Score", 9L);
        Map<String, Object> map3_2 = new HashMap<>();
        map3_2.put("id", "2");
        map3_2.put("name", "李三");
        map3_2.put("Score", 0L);
        Map<String, Object> map3_3 = new HashMap<>();
        map3_3.put("id", "3");
        map3_3.put("name", "王三");
        map3_3.put("Score", 86L);
        Map<String, Object> map2_4 = new HashMap<>();
        map2_4.put("id", "4");
        map2_4.put("name", "赵三");
        map2_4.put("Score", 920L);
        resultlist3.add(map3_1);
        resultlist3.add(map3_2);
        resultlist3.add(map3_3);
        resultlist3.add(map2_4);
        listMap3.put("3", resultlist3);
        tagsList.add(listMap3);

        System.out.println("未排序时的tagsList：" + tagsList);

        //先对每一个里的Score降序排序，排序后tagsList里的每一个元素中的List<Map<String, Object>>都是有序的
        for (Map<String, List<Map<String, Object>>> ins : tagsList) {
            for (List<Map<String, Object>> list : ins.values()) {
                list.sort((o1, o2) -> {
                    Long one = Long.valueOf(o1.get("Score").toString());
                    Long two = Long.valueOf(o2.get("Score").toString());
                    return two.compareTo(one);
                });
            }
        }
        System.out.println("对每一个Score内部降序排序后的tagsList：" + tagsList);

        tagsList.sort((o1, o2) -> {
            Long one = 0L;
            Long two = 0L;
            for (List<Map<String, Object>> v : o1.values()) {
                one = Long.valueOf(v.get(0).get("Score").toString());
                break;
            }
            for (List<Map<String, Object>> v : o2.values()) {
                two = Long.valueOf(v.get(0).get("Score").toString());
                break;
            }
            return two.compareTo(one);
        });
        System.out.println("根据最内层的最大值排序后的tagsList：" + tagsList);

    }
}
