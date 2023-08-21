package com.hai.tang.junit5test;


import com.hai.tang.model.Student;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Long.sum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("assert断言示例测试类")
public class AssertTest {

    @DisplayName("assert常见断言示例测试")
    @Test
    public void asserCommon() {
        //断言预期的 3 是否等于 sum(2,1)
        assertEquals(3, sum(2, 1));
        //断言预期的 2 是否等于 sum(1,1) ，如果断言失败会报错 org.opentest4j.AssertionFailedError: 断言失败啦！sum(1,1)得到的结果不是预期的2，请检查sum方法！
        assertEquals(2, sum(1, 1), "断言失败啦！sum(1,1)得到的结果不是预期的2，请检查sum方法！");

        //断言字符串相等
        assertEquals("张三", "张三");

        String str1 = new String("abc");
        String str2 = new String("abc");
        String str3 = "abc";
        String str4 = "abc";
        //断言 str3 和 str4 指向的对象一样
        assertSame(str3, str4);
        //断言 str1 和 str2 指向的对象不一样
        assertNotSame(str1, str2);

        //断言3 不等于 sum(2,2)
        assertNotEquals(3, sum(2, 2));
        //断言字符串不相等
        assertNotEquals("张三", "李四");

        //断言5小于6为真
        assertTrue(5 < 6);
        //断言5大于6为假
        assertFalse(5 > 6);

        String str5 = "你好";
        String str6 = null;
        //断言 str1 不为 null
        assertNotNull(str5);
        //断言 str2 为 null
        assertNull(str6);

        String[] expectedArray = {"one", "two", "three"};
        String[] resultArray = {"one", "two", "three"};
        //断言两个数组内容相等
        assertArrayEquals(expectedArray, resultArray);

        List<String> arrList = new ArrayList<>(Arrays.asList("Java", "Junit", "Test"));
        List<String> linList = new LinkedList<>(Arrays.asList("Java", "Junit", "Test"));
        //断言两个列表集合的元素内容相等（如果它们的顺序或大小不同则断言失败）
        assertIterableEquals(arrList, linList);

        //断言执行的方法不会超过预期2秒
        assertTimeout(Duration.ofSeconds(2), () -> {
                    Thread.sleep(1000);
                }
        );

    }


    @DisplayName("assertThat常见断言示例测试")
    @Test
    public void assertThatCommon() {
        //数字类型断言 只有a为大于10小于30并且等于22时，断言才成功
        int a = 22;
        assertThat(a)
                .isGreaterThan(10)
                .isLessThan(30)
                .isEqualTo(22);

        //字符断言 只有c不等于'a'，且在 Unicode 表中，且大于w(即在w之后)，且是小写 时断言才成功
        char c = 'x';
        assertThat(c)
                .isNotEqualTo('a')
                .inUnicode()
                .isGreaterThanOrEqualTo('w')
                .isLowerCase();

        //字符串断言 只有str1等于str2，并且str1长度是11，包含“123”，以“ab”开头，以“fg”结尾 时断言才成功
        String str1 = "abcd1234efg";
        String str2 = new String("abcd1234efg");
        assertThat(str1)
                .isEqualTo(str2)
                .hasSize(11)
                .contains("123")
                .startsWith("ab")
                .endsWith("fg");

        //boolean断言
        boolean bo1 = true;
        boolean bo2 = true;
        //只有bo1为true并且等于bo2时断言才成功
        assertThat(bo1)
                .isTrue()
                .isEqualTo(bo2);

        //List 断言 只有list1不为空（不为null且size大于0）且包含元素1，且包含元素3和4，且所有元素不为null，且等于list2 时，断言才成功
        List<String> list1 = Arrays.asList("1", "2", "3", "4");
        List<String> list2 = Arrays.asList("1", "2", "3", "4");
        assertThat(list1)
                .isNotEmpty()
                .contains("1")
                .containsSequence("3", "4")
                .doesNotContainNull()
                .isEqualTo(list2);

        //数组断言 只有array1不为null且包含元素3，且等于array2 ,且数组长度是3时，断言才成功
        String[] array1 = {"1", "2", "3"};
        String[] array2 = {"1", "2", "3"};
        assertThat(array1)
                .isNotNull()
                .contains("3")
                .isEqualTo(array2)
                .hasSize(3);

        //List 断言. 对 List 的每个元素过滤断言 只有list中每个元素都含有"-",并且至少有一个元素含有"ok"时，断言才成功
        List<String> list = Arrays.asList("1-a", "2-b", "3-c", "4-d", "5-e ok");
        assertThat(list)
                .allMatch(v -> v.contains("-"))
                .anyMatch(v -> v.contains("ok"));

        //List 断言. 对List进行过滤得到新的一个List进行断言
        List<Student> stuList1 = new ArrayList<>();
        stuList1.add(new Student("Jerry", 1));
        stuList1.add(new Student("Tom", 2));
        stuList1.add(new Student("Lucy", 3));

        //List 断言. 过滤筛选出 stuList 里所有 name 叫 Tom 的，然后断言其数量只有一个
        assertThat(stuList1)
                .filteredOn(v -> "Tom".equals(v.getName()))
                .hasSize(1);

        //List 断言. extracting 函数调用 getName 获取所有学生的姓名为一个新的List，然后只有这个新的List包含元素李四，且这个新是List等于nameList，断言才成功
        List<Student> stuList2 = new ArrayList<>();
        Student student1 = new Student("张三", 1);
        Student student2 = new Student("李四", 2);
        Student student3 = new Student("王五", 3);
        stuList2.add(student1);
        stuList2.add(student2);
        stuList2.add(student3);
        List<String> nameList = Arrays.asList("张三", "李四", "王五");
        assertThat(stuList2)
                .extracting(Student::getName)
                .contains("李四")
                .isEqualTo(nameList);

        //Map断言
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 5);
        map.put(2, 7);
        map.put(3, 15);
        //只有map不为空（不为null且size大于0），且包含key是2，且不包含key是10，且包含value是5，且包含键值分别是3和15时，断言才成功
        assertThat(map)
                .isNotEmpty()
                .containsKey(2)
                .doesNotContainKeys(10)
                .containsValue(5)
                .contains(MapEntry.entry(3, 15));


        //对象断言 . 只有student1的所有字段的值分别等于student2的字段的值时，断言才成功
        Student student4 = new Student("张三", 1);
        Student student5 = new Student("张三", 1);
        assertThat(student4)
                .usingRecursiveComparison()
                .isEqualTo(student5);


        //对象断言 .
        Student student = new Student("tom jeff", 4);
        //student的name字段的值包含jeff时，断言才成功
        assertThat(student.getName())
                .contains("jeff");
        //student的id字段的值大于3小于5时，断言才成功
        assertThat(student.getId())
                .isLessThan(5)
                .isGreaterThan(3);

    }

}
