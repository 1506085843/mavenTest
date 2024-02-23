package com.hai.tang.commonoperat;

import com.hai.tang.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@DisplayName("Stream流测试类")
public class StreamTest {

    @Test
    @DisplayName("List<String> 转 List<Integer>")
    public void listStrToInteger() {
        List<String> strList = new ArrayList<>(Arrays.asList("7", "8", "9"));
        List<Integer> integerList = strList.stream().map(Integer::valueOf).collect(Collectors.toList());
        System.out.println(integerList);
    }

    @Test
    @DisplayName("List<String> 转 Integer[ ]")
    public void listStrToIntegerArray() {
        List<String> strList = new ArrayList<>(Arrays.asList("7", "8", "9"));
        Integer[] intergerArry = strList.stream().map(Integer::valueOf).toArray(Integer[]::new);
        System.out.println(Arrays.toString(intergerArry));
    }

    @Test
    @DisplayName("String 转 List<Character>")
    public void listStrToCharacter() {
        String str = "abcdef";
        List<Character> list = str.chars().mapToObj(x -> (char) x).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    @DisplayName("String[]中的元素转大写并转为List<String>")
    public void arrayStrToList() {
        String[] strArray = {"java", "react", "angular", "vue"};
        List<String> list = Stream.of(strArray).map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    @DisplayName("对String字符串中的数字求和")
    public void stringSum() {
        String string = "Item1 10 Item2 25 Item3 30 Item4 45";
        Integer sum = Arrays.stream(string.split(" "))
                .filter((s) -> s.matches("\\d+"))
                .mapToInt(Integer::valueOf)
                .sum();
        System.out.println(sum);
    }

    @Test
    @DisplayName("String[] 转 List<String>")
    public void arrayStrToListStr() {
        String[] array = new String[]{"aa", "bb", "cc"};
        List<String> strList = Arrays.stream(array).collect(Collectors.toList());
        System.out.println(strList);
    }

    @Test
    @DisplayName("String[] 转 Integer []")
    public void arrayStrToInteger() {
        String[] strArry = new String[]{"5", "6", "1", "4", "9"};
        Integer[] integerArry = Arrays.stream(strArry).map(Integer::parseInt).toArray(Integer[]::new);
        System.out.println(Arrays.toString(integerArry));
    }

    @Test
    @DisplayName("List<Integer> 转 int []")
    public void listIntegerToInt() {
        List<Integer> integerList = new ArrayList<>(Arrays.asList(5, 6, 1, 4, 9));
        int[] arr = integerList.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(arr));
    }

    @Test
    @DisplayName("int [] 转 List<Integer>")
    public void arrayIntToListInteger() {
        int[] intArry = new int[]{5, 6, 1, 4, 9};
        List<Integer> integerList = Arrays.stream(intArry).boxed().collect(Collectors.toList());
        System.out.println(integerList);
    }

    @Test
    @DisplayName("int [] 转 Integer []")
    public void arrayIntToInteger() {
        int[] intArry = new int[]{5, 6, 1, 4, 9};
        Integer[] integerArry = Arrays.stream(intArry).boxed().toArray(Integer[]::new);
        System.out.println(Arrays.toString(integerArry));
    }

    @Test
    @DisplayName("int [] 最大、最小值、平均值，求和")
    public void arrayIntStatistics() {
        int[] arr = new int[]{12, 3, 34, 67, 100, 99};
        int maxValue = Arrays.stream(arr).max().getAsInt();
        int minValue = Arrays.stream(arr).min().getAsInt();
        double averValue = Arrays.stream(arr).average().getAsDouble();
        int sumValue = Arrays.stream(arr).sum();
        System.out.println("the max:" + maxValue);
        System.out.println("the min:" + minValue);
        System.out.println("the average:" + averValue);
        System.out.println("the sum:" + sumValue);

        //或者
/*      int[] intArray = {12,3,34,67,100,99};
        IntStream intStream = IntStream.of(intArray);
        IntSummaryStatistics statistics = intStream.summaryStatistics();
        System.out.println("the max:" + statistics.getMax());
        System.out.println("the min:" + statistics.getMin());
        System.out.println("the average:" + statistics.getAverage());
        System.out.println("the sum:" + statistics.getSum());
        //statistics.getCount()相当于获取数组大小
        System.out.println("the count:" + statistics.getCount());
*/
    }

    @Test
    @DisplayName("String[] 对每个元素分割并转换为其他类型")
    public void arrayStrSplitToOther() {
        String[] array = {"a-1", "b-2", "c-3"};

        //"-分割后获取字母，转为新数组
        String[] strArray = Arrays.stream(array).map(v -> v.split("-")[0]).toArray(String[]::new);
        //"-分割后获取字母，转List
        List<String> list = Arrays.stream(array).map(v -> v.split("-")[0]).collect(Collectors.toList());
        //"-分割后获取数字，转List
        List<Integer> numberlist = Arrays.stream(array).map(v -> Integer.parseInt(v.split("-")[1])).collect(Collectors.toList());
        //"-分割后获取字母，用逗号拼接为字符串
        String str = Arrays.stream(array).map(v -> v.split("-")[0]).collect(Collectors.joining(","));

        System.out.println(Arrays.toString(strArray));//[a, b, c]
        System.out.println(list);//[a, b, c]
        System.out.println(numberlist);//[1, 2, 3]
        System.out.println(str);//a,b,c
    }

    @Test
    @DisplayName("List<String> 逗号拼接为一个字符串")
    public void listStrToStr() {
        List<String> strList = new ArrayList<>(Arrays.asList("a", "b", "c"));
        String str = strList.stream().collect(Collectors.joining(","));
        // 或者使用 String.join
        // String str = String.join(",", strList);
        System.out.println(str);//a,b,c
    }

    @Test
    @DisplayName("List<Integer> 逗号拼接为一个字符串")
    public void listIntegerToStr() {
        List<Integer> integerList = Arrays.asList(7, 8, 9);
        String str = integerList.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(str);//7,8,9
    }

    @Test
    @DisplayName("生成指定范围的数组或List")
    public void generateArrayOrList() {
        //生成生成[0,100)的 数组或 List，包括 0， 不包括 100
        int[] intArray = IntStream.range(0, 100).toArray();
        List<Integer> intList = IntStream.range(0, 100)
                .boxed()
                .collect(Collectors.toList());
        List<String> strList = IntStream.range(0, 100)
                .boxed()
                .map(Object::toString)
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(intArray));
        System.out.println(intList);
        System.out.println(strList);

        //生成[0,100]的 数组或 List
        int[] intArray1 = IntStream.rangeClosed(0, 100).toArray();
        List<Integer> intList1 = IntStream.rangeClosed(0, 100)
                .boxed()
                .collect(Collectors.toList());
        List<String> strList1 = IntStream.rangeClosed(0, 100)
                .boxed()
                .map(i -> i.toString())
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(intArray1));
        System.out.println(intList1);
        System.out.println(strList1);
    }

    @Test
    @DisplayName("判断数组中是否含有某一值")
    public void arrayIsContain() {
        //字符串数组
        String[] strArray = {"AB", "BC", "CD", "AE"};
        boolean contains1 = Arrays.stream(strArray).anyMatch("AE"::equals);
        //或者
        //boolean contains1 = Arrays.asList(strArray).contains("AE");
        System.out.println(contains1);

        int[] intArray = {1, 2, 3, 4};
        boolean contains2 = IntStream.of(intArray).anyMatch(x -> x == 4);
        System.out.println(contains2);
    }

    @Test
    @DisplayName("数组或List求和")
    public void arrayListSum() {
        //数组求和
        int[] intArray = {11, 5, 3, 2, 1};
        int sum1 = Arrays.stream(intArray).reduce(0, Integer::sum);
        System.out.println(sum1);

        //List求和
        List<Integer> list = new ArrayList<>(Arrays.asList(11, 5, 3, 2, 1));
        int sum2 = list.stream().reduce(0, Integer::sum);
        System.out.println(sum2);
    }

    @Test
    @DisplayName("两个数组合并为一个新的数组")
    public void arraymMrgeToArray() {
        //String类型数组合并
        String[] a = {"a", "b", "c"};
        String[] b = {"1", "2", "3"};
        String[] c = Stream.of(a, b).flatMap(Stream::of).toArray(String[]::new);
        System.out.println(Arrays.toString(c));

        //int类型数组合并
        int[] a1 = new int[]{1, 3};
        int[] b1 = new int[]{2, 4};
        int[] c1 = IntStream.concat(Arrays.stream(a1), Arrays.stream(b1)).toArray();
        System.out.println(Arrays.toString(c1));
    }

    @Test
    @DisplayName("两个数组合并为一个List")
    public void arrayMergeToList() {
        //String类型数组合并
        String[] a = {"a", "b", "c"};
        String[] b = {"1", "2", "3"};
        List<String> strList = Stream.of(a, b).flatMap(Stream::of).collect(Collectors.toList());
        System.out.println(strList);

        //int类型数组合并
        int[] a1 = new int[]{1, 2};
        int[] b1 = new int[]{3, 4};
        List<Integer> integerList = Stream.of(IntStream.of(a1).boxed(), IntStream.of(b1).boxed()).flatMap(s -> s).collect(Collectors.toList());
        System.out.println(integerList);
    }

    @Test
    @DisplayName("两个数组合并为一个List")
    public void listMergeToList() {
        List<String> lis1 = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> list2 = new ArrayList<>(Arrays.asList("e", "f", "g"));
        List<String> newList = Stream.of(lis1, list2).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(newList);
    }

    @Test
    @DisplayName("List<Integer>求交集、并集、差集")
    public void listHandle() {
        List<Integer> list = new ArrayList<>(Arrays.asList(7, 8, 9));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(3, 4, 9));
        //交集
        List<Integer> beMixed = list.stream().filter(list2::contains).collect(Collectors.toList());
        System.out.println(beMixed);//[9]

        //并集
        List<Integer> aggregate = Stream.of(list, list2).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        System.out.println(aggregate);//[7, 8, 9, 3, 4]

        //差集
        List<Integer> subtraction = list.stream().filter(v -> !list2.contains(v)).collect(Collectors.toList());
        System.out.println(subtraction);//[7, 8]
    }


    @Test
    @DisplayName("List<String>中统计首字母是j的个数")
    public void listCountStartChat() {
        List<String> list = Arrays.asList("java", "react", "angular", "javascript", "vue");
        long count = list.stream().filter(p -> p.startsWith("j")).count();
        System.out.println(count);
    }

    @Test
    @DisplayName("List<String>中获取第一个首字母是j的元素")
    public void listFirstCountStartChat() {
        List<String> list = Arrays.asList("react", "java", "angular", "javascript", "vue");
        String firstJ = list.stream().filter(p -> p.startsWith("j")).findFirst().get();
        System.out.println(firstJ);
    }

    @Test
    @DisplayName("List统计各元素次数")
    public void listStatisticsCount() {
        List<String> items = Arrays.asList("apple", "apple", "orange", "orange", "orange", "blueberry", "peach", "peach", "peach", "peach");
        Map<String, Long> result = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        //{orange=3, apple=2, blueberry=1, peach=4}
        System.out.println(result);
    }

    @Test
    @DisplayName("List去除重复元素")
    public void distinctList() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "bb");
        List<String> distinctList = list.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctList);
    }

    @Test
    @DisplayName("List<List<String>>转List<String>")
    public void doubleListToList() {
        List<String> a = Arrays.asList("Virat", "Dhoni", "Jadeja");
        List<String> b = Arrays.asList("1", "2", "3");
        List<List<String>> list = new ArrayList<>();
        list.add(a);
        list.add(b);

        List<String> newList = list.stream().flatMap(Collection::stream).collect(Collectors.toList());
        //[Virat, Dhoni, Jadeja, 1, 2, 3]
        System.out.println(newList);
    }


    @SafeVarargs
    public static List<String> join(List<String>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Test
    @DisplayName("多个List<String> 合并为一个List<String>")
    public void manyListToList() {
        List<String> list1 = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> list2 = new ArrayList<>(Arrays.asList("d", "e", "f"));
        List<String> list3 = new ArrayList<>(Arrays.asList("g", "h", "i"));
        List<String> list4 = new ArrayList<>(Arrays.asList("1", "2"));
        List<String> list5 = new ArrayList<>(Arrays.asList("3", "4", "5"));

        List<String> result1 = join(list1, list2, list3);
        List<String> result2 = join(list4, list5);
        //[a, b, c, d, e, f, g, h, i]
        System.out.println(Arrays.toString(result1.toArray()));
        //[1, 2, 3, 4, 5]
        System.out.println(Arrays.toString(result2.toArray()));
    }

    @Test
    @DisplayName("List<Object>针对某一成员变量获取最大、小值的Object")
    public void listGetMaxMin() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "张三", 85));
        list.add(new Student(2, "李四", 60));
        list.add(new Student(3, "刘一", 70));
        list.add(new Student(4, "李四", 99));
        //获取 score 最大的 Student
        Student maxStudent = list.stream().max(Comparator.comparing(Student::getScore)).get();
        System.out.println(maxStudent.getScore());
        //获取 score 最小的 Student
        Student minxStudent = list.stream().min(Comparator.comparing(Student::getScore)).get();
        System.out.println(minxStudent.getScore());
    }

    @Test
    @DisplayName("List<Object>获取某个成员变量最大、最小值、平均值，求和")
    public void listStatistics() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "小名", 17));
        list.add(new Student(2, "小红", 18));
        list.add(new Student(3, "小蓝", 19));
        list.add(new Student(4, "小灰", 20));
        list.add(new Student(5, "小黄", 21));
        list.add(new Student(6, "小白", 22));

        IntSummaryStatistics intSummary = list.stream().collect(Collectors.summarizingInt(Student::getScore));
        System.out.println(intSummary.getAverage());// 19.5
        System.out.println(intSummary.getMax());// 22
        System.out.println(intSummary.getMin());// 17
        System.out.println(intSummary.getSum());// 117

        //如果你只想求和可以像下面这样写：
        //Integer sum = list.stream().mapToInt(Student::getScore).sum();//117
        //下面注释的两行代码和上一行的效果一样，都可以进行求和
        //Integer sum = list.stream().map(Student::getScore).reduce(0, Integer::sum);
        //Integer sum = list.stream().map(Student::getScore).mapToInt(Integer::intValue).sum();
    }

    @Test
    @DisplayName("List<Integer> 获取最大、最小值、平均值，求和")
    public void listIntegerStatistics() {
        List<Integer> list = Arrays.asList(12, 3, 34, 67, 100, 99);
        int maxValue = list.stream().max(Integer::compare).get();
        int minValue = list.stream().min(Integer::compare).get();
        double averValue = list.stream().mapToDouble(a -> a).average().getAsDouble();
        int sumValue = list.stream().reduce(0, Integer::sum);
        System.out.println("the max:" + maxValue);
        System.out.println("the min:" + minValue);
        System.out.println("the average:" + averValue);
        System.out.println("the sum:" + sumValue);

        //还可以像下面这样获取最大值、最小值、平均值，求和
      /* IntSummaryStatistics statistics = list.stream().mapToInt(value -> value).summaryStatistics();
        System.out.println("the max:" + statistics.getMax());
        System.out.println("the min:" + statistics.getMin());
        System.out.println("the average:" + statistics.getAverage());
        System.out.println("the sum:" + statistics.getSum());
        System.out.println("the count:" + statistics.getCount());//获取list大小
        */
    }

    @Test
    @DisplayName("Map的value保存为List、Set")
    public void mapValueToListSet() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");

        List<String> valuesList = new ArrayList<>(map.values());
        //或者 List<String> valuesList = map.values().stream().collect(Collectors.toList());
        System.out.println(valuesList);

        Set<String> valuesSet = new HashSet<>(map.values());
        //或者 Set<String> valuesSet = map.values().stream().collect(Collectors.toSet());
        System.out.println(valuesSet);
    }

    @Test
    @DisplayName("Map对value求和")
    public void mapValueSum() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 11);
        map.put("2", 22);
        map.put("3", 100);
        Integer sum = map.values().stream().mapToInt(Integer::valueOf).sum();
        System.out.println(sum);
    }

    @Test
    @DisplayName("Map<String, List<Integer>>获取所有的values为一个List<Integer>")
    public void mapValueToList() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(4, 5, 6));
        List<Integer> list3 = new ArrayList<>(Arrays.asList(7, 8, 9));

        Map<String, List<Integer>> map = new HashMap<>();
        map.put("a", list1);
        map.put("b", list2);
        map.put("c", list3);

        List<Integer> list = map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        //下面的代码是一样的效果
        //List<Integer> list = map.entrySet().stream().map(e -> e.getValue()).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(list);//[1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    @Test
    @DisplayName("List<Object>转Map<String, Object>")
    public void listToMap() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "张三", 85));
        list.add(new Student(2, "李四", 60));
        Map<String, Student> map = list.stream().collect(Collectors.toMap(Student::getName, Function.identity()));
        System.out.println(map);
    }

    @Test
    @DisplayName("判断字符串是否List中的任何一个元素")
    public void listAnyMatch() {
        String str ="谁说王五不会打篮球？";
        List<String> searchList = Arrays.asList("张三","李四","王五");//要搜索的字符串
        if (searchList.stream().anyMatch(str::contains)) {
            System.out.println("str至少含有 searchList 的一个元素");
        } else {
            System.out.println("str 不含有 searchList 的任何元素");
        }
    }
}
