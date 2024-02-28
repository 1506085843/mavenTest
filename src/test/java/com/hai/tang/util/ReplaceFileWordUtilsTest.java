package com.hai.tang.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@DisplayName("查找并替换文本文件中字符串的测试类")
public class ReplaceFileWordUtilsTest {

    @Test
    @DisplayName("获取文件内容为一个字符串")
    public void getFileContent() {
        String filePath = "D:\\text1.txt";
        String fileContent = ReplaceFileWordUtils.getFileContent(filePath);
    }

    @Test
    @DisplayName("将字符串写入文件")
    public void writeFileContent() {
        String filePath = "D:\\text1.txt";
        String fileContent = "你好";
        ReplaceFileWordUtils.writeFileContent(filePath,fileContent);
    }

    @Test
    @DisplayName("在文本文件 filePath 中搜索第一次出现的 searchWord，并将其替换为 replaceWord")
    public void replaceWordFirst() {
        String filePath = "D:\\text1.txt";
        ReplaceFileWordUtils.replaceWordFirst(filePath,"经典散文经典文章大全1","李四哈哈");
    }

    @Test
    @DisplayName("在文本文件 filePath 中搜索所有的 searchWord，并将其替换为 replaceWord")
    public void replaceWordAll() {
        String filePath = "D:\\text1.txt";
        ReplaceFileWordUtils.replaceWordAll(filePath,"经典散文经典文章大全1","李四哈哈");
    }


    @Test
    @DisplayName("在文本文件 filePath 中搜索所有的 searchReplaceMap 的键，并将其替换为对应的值")
    public void replaceMultWordAll() {
        String filePath = "D:\\text1.txt";
        Map<String,String> searchReplaceMap = new HashMap<>();
        searchReplaceMap.put("《经典","aa");
        searchReplaceMap.put("《优美","bb");
        ReplaceFileWordUtils.replaceMultWordAll(filePath,searchReplaceMap);
    }

    @Test
    @DisplayName("在文本文件 filePath 中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord xxxxxx searchEndWord替换为 replaceWord")
    public void replaceStartEnd() {
        String filePath = "D:\\text1.txt";
        //将文本中的《经典xxxxx1》 替换为 a李四哈哈
        ReplaceFileWordUtils.replaceStartEnd(filePath,"《经典","1》","a李四哈哈");
    }

    @Test
    @DisplayName("在文本文件 filePath 中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将 键xxx值 替换为 replaceWord")
    public void replaceStartEnds() {
        String filePath = "D:\\text1.txt";
        Map<String,String> startEndWordsMap = new HashMap<>();
        startEndWordsMap.put("《经典","1》");
        startEndWordsMap.put("《优美","精选》");
        //将文本中的《经典xxx1》、《优美xxx精选》 替换为 a李四哈哈
        ReplaceFileWordUtils.replaceStartEnds(filePath,startEndWordsMap,"a李四哈哈");
    }

    @Test
    @DisplayName("在文本文件 filePath 中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord 和 searchEndWord 之间的 xxx 替换为 replaceWord")
    public void replaceBetween() {
        String filePath = "D:\\text1.txt";
        //将文本中的《经典xxx1》替换为 《经典a李四哈哈1》
        ReplaceFileWordUtils.replaceBetween(filePath,"《经典","1》","a李四哈哈");
    }

    @Test
    @DisplayName("在文本文件 filePath 中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将键值中间的 xxx 替换为 replaceWord")
    public void replaceBetweens() {
        String filePath = "D:\\text1.txt";
        Map<String,String> map = new HashMap<>();
        map.put("《经典","1》");
        map.put("《优美","精选》");
        //将文本中的《经典xxx1》、《优美xxx精选》 分别替换为 《经典a李四哈哈1》、《优美a李四哈哈精选》
        ReplaceFileWordUtils.replaceBetweens(filePath,map,"a李四哈哈");
    }
}
