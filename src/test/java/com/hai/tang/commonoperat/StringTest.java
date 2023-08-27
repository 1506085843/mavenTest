package com.hai.tang.commonoperat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("字符串测试类")
public class StringTest {

    @Test
    @DisplayName("查询字符串中某字符第一次出现的位置，不存在则返回 -1")
    public void indexOf() {
        String str = "abc${def}ab}c";
        int index1 = str.indexOf("}");  //将返回8
        int index2 = str.indexOf(99);   //将返回2。99为字符“c”的ascall码值，等同于str.indexOf("c")
        System.out.println(index1);
        System.out.println(index2);
    }

    @Test
    @DisplayName("从字符串的指定位置开始查询字符第一次出现的位置，不存在则返回 -1")
    public void indexOf1() {
        String str = "abc${def}ab}c";
        int index1 = str.indexOf("a", 3);  //将返回9。从下标为3即$开始查找“a”的位置
        int index2 = str.indexOf(97, 3);  //将返回9。97为字符“a”的ascall码
        System.out.println(index1);
        System.out.println(index2);
    }

    @Test
    @DisplayName("查找字符串中字符最后一次出现的位置，不存在则返回 -1")
    public void lastIndexOf() {
        String str = "abc${def}ab}c";
        int lastPostion = str.lastIndexOf("}"); // 将返回11,不存在则返回-1
        System.out.println(lastPostion);
    }

    @Test
    @DisplayName("从字符串的指定位置截取后面字符串")
    public void substring() {
        String str = "abc${def}ab}c";
        String sub1 = str.substring(3); //截取下标3后面的字符串，返回${def}ab}c
        String sub2 = str.substring(1, 4);//截取下标[1,4）位置的字符串，将返回bc$
        String sub3 = str.substring(str.indexOf("{") + 1, str.indexOf("}"));//获取花括号中的字符串，将返回def
        String sub4 = str.substring(str.indexOf("{") + 1, str.indexOf("}", str.indexOf("}") + 1));//截取字符串中第一个“{”到第二个“}”间的字符串,将返回def}ab

        System.out.println(sub1);
        System.out.println(sub2);
        System.out.println(sub3);
        System.out.println(sub4);
    }

    @Test
    @DisplayName("字符串翻转")
    public void reversestring() {
        String str = "abc${def}ab}c";
        String reverse = new StringBuffer(str).reverse().toString();//将返回 c}ba}fed{$cba
        System.out.println(reverse);
    }

    @Test
    @DisplayName("或者字符串中指定位置的字符为字符串")
    public void getCharToString() {
        String str = "abc${def}ab}c";
        String getC = String.valueOf(str.charAt(2));//将返回c
        System.out.println(getC);
    }

    @Test
    @DisplayName("字符型的数字转数字")
    public void getCharToNumber() {
        char ch = '3';
        int chNamber = ch - '0';// 3
        System.out.println(chNamber);
    }

    @Test
    @DisplayName("判断字符是不是一个数字")
    public void charIsNumber() {
        char ch = '3';
        boolean isOrNo = Character.isDigit(ch);//isOrNo结果为true
        System.out.println(isOrNo);
    }

    @Test
    @DisplayName("替换a到b之间的字符串")
    public void replaceSubstring() {
        String str = "abc${def}ab}c";
        StringBuilder build = new StringBuilder(str);
        build.replace(5, 8, "123456");//将[5,8)位置的字符串替换为123456
        str = build.toString();
        System.out.println(str);//输出abc${123456}ab}c
    }

}
