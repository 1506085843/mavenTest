package com.hai.tang.commonoperat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    @DisplayName("替换字符串某一下标处的字符")
    public void replaceChar() {
        String str = "abeecsabxt";
        StringBuilder strBuild = new StringBuilder(str);
        strBuild.setCharAt(1, '$');
        String  result = String.valueOf(strBuild);
        System.out.println(result); //a$eecsabxt
    }

    @Test
    @DisplayName("在字符串指定位置之前插入字符(串)")
    public void insertStr() {
        String str = "abeecsabxt";
        StringBuilder strBuild = new StringBuilder(str);
        strBuild.insert(1, "qq"); //插入字符串
        //strBuild.insert(1, 'q'); //插入字符
        String  result = String.valueOf(strBuild);
        System.out.println(result);//输出：aqqbeecsabxt
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
    @DisplayName("判断字符串是不是数字")
    public void stringIsNumber() {
        String str = "-100.864564009";
        if (str.matches("-?\\d+(\\.\\d+)?")) {
            System.out.println("是数字");
        } else {
            System.out.println("不是数字");
        }

        //或者
        /*try {
            String str = "-100.864564009";
            double num = Double.parseDouble(str);
            System.out.println("是数字");
        } catch (NumberFormatException e) {
            System.out.println("不是数字");
        }*/
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


    @Test
    @DisplayName("字符串中分割")
    public void stringSplit() {
        String str = "abc def   gh.ij   k/l   m//$\\s";

        //通过空格来分割
        String[] split = str.split("\\s+");
        System.out.println(Arrays.toString(split));//[abc, def, gh.ij, k/l, m//$\s]
        //通过 . 来分割
        String[] split1 = str.split("\\.");
        System.out.println(Arrays.toString(split1));//[abc def   gh, ij   k/l   m//$\s]
        //通过 $ 来分割
        String[] split2 = str.split("\\$");
        System.out.println(Arrays.toString(split2));//[abc def   gh.ij   k/l   m//, \s]
        //通过 \ 来分割
        String[] split3 = str.split("\\\\");
        System.out.println(Arrays.toString(split3));//[abc def   gh.ij   k/l   m//$, s]
    }

    @Test
    @DisplayName("字符串中替换特殊字符")
    public void stringReplace() {
        String str = "abc def   gh.ij   k/l   m//$\\siug\\\\h";

        //替换空格为 1
        String str1 = str.replaceAll("\\s+","1");
        System.out.println(str1);   //abc1def1gh.ij1k/l1m//$\siug\\h
        //替换 . 为 2
        String str2 = str.replaceAll("\\.","2");
        System.out .println(str2);  //abc def   gh2ij   k/l   m//$\siug\\h
        //替换 $ 为3
        String str3 = str.replaceAll("\\$","3");
        System.out.println(str3);   //abc def   gh.ij   k/l   m//3\siug\\h
        //替换 \ 为 4
        String str4 = str.replaceAll("\\\\","4");
        System.out.println(str4);   //abc def   gh.ij   k/l   m//$4siug44h
        //替换 \\ 为 5
        String str5 = str.replaceAll("\\\\\\\\","5");
        System.out.println(str5);   //abc def   gh.ij   k/l   m//$\siug5h
    }

    @Test
    @DisplayName("字符串中替换第一次遇到的特殊字符")
    public void stringReplaceFirst() {
        String str = "abc  def   gh.ij   k/l   m//$\\siug\\\\h";

        //将遇到的第一个多空格替换为 1
        String str1 = str.replaceFirst("\\s+","1");
        System.out.println(str1);   //abc1def   gh.ij   k/l   m//$\siug\\h
        //将遇到的第一个 . 替换为 2
        String str2 = str.replaceFirst("\\.","2");
        System.out .println(str2);  //abc  def   gh2ij   k/l   m//$\siug\\h
        //将遇到的第一个 $ 替换为3
        String str3 = str.replaceFirst("\\$","3");
        System.out.println(str3);   //abc  def   gh.ij   k/l   m//3\siug\\h
        //将遇到的第一个 \ 替换为 4
        String str4 = str.replaceFirst("\\\\","4");
        System.out.println(str4);   //abc  def   gh.ij   k/l   m//$4siug\\h
        //将遇到的第一个 \\ 替换为 5
        String str5 = str.replaceFirst("\\\\\\\\","5");
        System.out.println(str5);   //abc  def   gh.ij   k/l   m//$\siug5h
    }

    @Test
    @DisplayName("对字符串特殊字符转义")
    public void stringEscape() {
        String str = "ab$c  dk`jge`f   g</script>h.ij   k/l   m//$\\siug\\\\h";

        //反斜杠对其转义为\\
        String str1 = str.replace("\\", "\\\\");
        // 或者 String str1 = str.replaceAll("\\\\", "\\\\\\\\");
        System.out.println(str1);   //abc  dk`jge`f   g</script>h.ij   k/l   m//$\\siug\\\\h

        //</script>将其转义为<\/script>
        String str2 = str.replace("</script>", "<\\/script>");
        //或者 String str2 = str.replaceAll("</script>", "<\\\\/script>");
        System.out.println(str2);   //abc  dk`jge`f   g<\/script>h.ij   k/l   m//$\siug\\h

        //字符串模板的反引号`对其转义为\`
        String str3 = str.replace("`", "\\`");
        //或者 String str3 = str.replaceAll("`", "\\\\`");
        System.out.println(str3);  //abc  dk\`jge\`f   g</script>h.ij   k/l   m//$\siug\\h

        //$符号对其转义为\$
        String str4 = str. replace("$", "\\$");
        // 或者 String str4 = str.replaceAll("\\$", "\\\\\\$");
        System.out.println(str4);   //abc  dk`jge`f   g</script>h.ij   k/l   m//\$\siug\\h
    }

    @Test
    @DisplayName("字符串正则匹配与连接")
    public void stringRegularLink() {
        //匹配字符串中所有方括号里的时间然后用~连接两个时间
        String input = "结算组织：张三有限公司测试;开票日期范围：【2023-09-01   2023-09-02】; 单据日期范围：【2023-09-04 2023-09-06】; 生成凭证日期：【2023-09-9 2023-09-11】";

        Pattern pattern = Pattern.compile("【([^【】]+)】");
        Matcher matcher = pattern.matcher(input);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String dateText = matcher.group(1);
            String concatenatedDate = dateText.replaceAll("\\s+", "~");
            matcher.appendReplacement(result, "【" + concatenatedDate + "】");
        }
        matcher.appendTail(result);
        //输出：结算组织：张三有限公司测试;开票日期范围：【2023-09-01~2023-09-02】; 单据日期范围：【2023-09-04~2023-09-06】; 生成凭证日期：【2023-09-9~2023-09-11】
        System.out.println(result);
    }

    @Test
    @DisplayName("字符串正则匹配")
    public void stringRegular() {
        // 匹配【】括号中的内容保存到 resultList 中
        String input = "结算组织：张三有限公司测试;开票日期范围：【2023-09-01   2023-09-02】; 单据日期范围：【2023-09-04 2023-09-06】; 生成凭证日期：【2023-09-9 2023-09-11】";

        String regex = "【([^】]+)】";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 存储匹配结果的List
        List<String> resultList = new ArrayList<>();

        // 遍历匹配结果，将括号中的内容添加到List中
        while (matcher.find()) {
            String match = matcher.group(1);
            resultList.add(match.trim());
        }
        //输出：[2023-09-01   2023-09-02, 2023-09-04 2023-09-06, 2023-09-9 2023-09-11]
        System.out.println(resultList);
    }

    @Test
    @DisplayName("字符串中查找是否至少包含List的一个元素")
    public void bstTest1() {
        String str = "我们经历了一场兵慌马乱的战争，没有硝烟，没有流血，却伤亡惨重。青春是美好的，带给了我们很多的快乐和激情，可是青春又是悲伤的，当有一天青春不在了，我们会拼命的去怀念。";
        List<String> listA = Arrays.asList("经历", "兵慌马乱", "硝烟","快乐","悲伤");
        List<String> listB = Arrays.asList("aa", "bb", "cc","dd","悲伤");

        boolean containsInStrA = listA.stream().anyMatch(str::contains);
        boolean containsInStrB = listB.stream().anyMatch(str::contains);
        System.out.println("str字符串至少包含listA中的一个元素: " + containsInStrA);  // 输出: str字符串至少包含listA中的一个元素:  true
        System.out.println("str字符串至少包含listB中的一个元素: " + containsInStrB);  // 输出: str字符串至少包含listB中的一个元素:  true
    }

    @Test
    @DisplayName("字符串中查找包含的List元素")
    public void bstTest2() {
        String str = "我们经历了一场兵慌马乱的战争，没有硝烟，没有流血，却伤亡惨重。青春是美好的，带给了我们很多的快乐和激情，可是青春又是悲伤的，当有一天青春不在了，我们会拼命的去怀念。";
        List<String> list = Arrays.asList("aa", "bb", "cc","快乐","悲伤");

        List<String> result = list.stream().filter(str::contains).collect(Collectors.toList());
        System.out.println("str中包含的list元素有: " + result);//str中包含的list元素有: [快乐, 悲伤]
    }
}
