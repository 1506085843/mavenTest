package com.hai.tang.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ReplaceStrWordUtilsTest {
    @Test
    @DisplayName("在字符串中搜索第一次出现的 searchWord，并将其替换为 replaceWord")
    public void replaceWordFirst() {
        //获取文件内容为一个字符串
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");
        //将 fileContent 中第一次出现的的" 经典散文经典文章大全1" 替换为 "李四哈哈"
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceWordFirst("经典散文经典文章大全1", "李四哈哈");
        //获取替换后的结果
        String result = replace.get();
        System.out.println(result);
        //将结果写入文件
        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }

    @Test
    @DisplayName("在字符串中搜索所有的 searchWord，并将其替换为 replaceWord")
    public void replaceWordAll() {
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");

        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceWordAll("经典散文经典文章大全1", "李四哈哈");
        String result = replace.get();
        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }


    @Test
    @DisplayName("在字符串中搜索所有的 searchReplaceMap 的键，并将其替换为对应的值")
    public void replaceMultWordAll() {
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");

        Map<String, String> searchReplaceMap = new HashMap<>();
        searchReplaceMap.put("《经典", "aa");
        searchReplaceMap.put("《优美", "bb");
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceMultWordAll(searchReplaceMap);
        String result = replace.get();

        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }

    @Test
    @DisplayName("在字符串中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord xxxxxx searchEndWord替换为 replaceWord")
    public void replaceStartEnd() {
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");
        //将文本中的《经典xxxxx1》 替换为 a李四哈哈
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceStartEnd("《经典", "1》", "a李四哈哈");
        String result = replace.get();

        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }

    @Test
    @DisplayName("在字符串中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将 键xxx值 替换为 replaceWord")
    public void replaceStartEnds() {
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");
        Map<String, String> startEndWordsMap = new HashMap<>();
        startEndWordsMap.put("《经典", "1》");
        startEndWordsMap.put("《优美", "精选》");
        //将文本中的《经典xxx1》、《优美xxx精选》 替换为 a李四哈哈
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceStartEnds(startEndWordsMap, "a李四哈哈");
        String result = replace.get();

        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }

    @Test
    @DisplayName("在字符串中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord 和 searchEndWord 之间的 xxx 替换为 replaceWord")
    public void replaceBetween() {
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");
        //将文本中的《经典xxx1》替换为 《经典a李四哈哈1》
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceBetween("《经典", "1》", "a李四哈哈");
        String result = replace.get();

        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }

    @Test
    @DisplayName("在字符串中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将键值中间的 xxx 替换为 replaceWord")
    public void replaceBetweens() {
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");
        Map<String, String> map = new HashMap<>();
        map.put("《经典", "1》");
        map.put("《优美", "精选》");
        //将文本中的《经典xxx1》、《优美xxx精选》 分别替换为 《经典a李四哈哈1》、《优美a李四哈哈精选》
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceBetweens(map, "a李四哈哈");
        String result = replace.get();

        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }

    @Test
    @DisplayName("在字符串中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将键值中间的 xxx 替换为 replaceWord")
    public void replaceOther() {
        //获取文件内容为一个字符串
        String fileContent = ReplaceFileWordUtils.getFileContent("D:\\text1.txt");

        Map<String, String> betweensMap = new HashMap<>();
        betweensMap.put("<Version>", "</Version>");
        betweensMap.put("<ModifyDate>", "</ModifyDate>");
        Map<String, String> startEndMap = new HashMap<>();
        startEndMap.put("你", "呀");
        startEndMap.put("多少", "想要");

        //将 fileContent 字符串中第一次出现的 "张三" 替换为 "李四"
        //将所有 "经典散文" 替换为 "诗集大全" 。
        //将"<Version>xxx</Version>"、"<<ModifyDate>>xxx</<ModifyDate>>" 替换为 "<Version>2024.2.28</Version>"、"<<ModifyDate>>2024.2.28</<ModifyDate>>"
        //将"你xxx呀"、"多少xxx想要" 替换为 "曾经沧海"
        ReplaceStrWordUtils replace = new ReplaceStrWordUtils(fileContent);
        replace.replaceWordFirst("张三", "李四")
        .replaceWordAll("经典散文", "诗集大全")
        .replaceBetweens(betweensMap, "2024.2.28")
        .replaceStartEnds(startEndMap, "曾经沧海");

        //获取替换后的字符串并写入文件
        String result = replace.get();
        ReplaceFileWordUtils.writeFileContent("D:\\text1.txt", result);
    }
}
