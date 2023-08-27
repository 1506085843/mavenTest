package com.hai.tang.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayName("文件扫描及多文件中查找字符串测试类")
public class SearchWordUtilsTest {

    @DisplayName("扫描文件夹下含有的所有文件")
    @Test
    public void searchAllFils() {
        String dir = "D:\\迅雷下载\\";
        List<String> filesPath = SearchWordUtils.getAllFilesPath(dir);
        System.out.println();
        filesPath.forEach(System.out::println);
    }

    @DisplayName("扫描除排查文件夹外，文件夹下含有的所有文件")
    @Test
    public void searchAllFils_exclude() {
        String dir = "D:\\迅雷下载\\";
        List<String> excludeDir = Arrays.asList("layDate-v5.3.1");
        List<String> filesPath = SearchWordUtils.getAllFilesPathEx(dir, excludeDir);
        System.out.println();
        filesPath.forEach(System.out::println);

    }

    @DisplayName("扫描文件夹下指定后缀类型的所有文件")
    @Test
    public void searchAllFils_speciFiles() {
        String dir = "D:\\迅雷下载\\";
        List<String> filesType = Arrays.asList("jar", "zip", "txt");
        List<String> filesPath = SearchWordUtils.getAllFilesPath(dir, filesType);
        System.out.println();
        filesPath.forEach(System.out::println);
    }

    @DisplayName("扫描除排查文件夹外，文件夹下指定后缀类型的所有文件")
    @Test
    public void searchAllFils_exclude_speciFiles() {
        String dir = "D:\\迅雷下载\\";
        List<String> filesType = Arrays.asList("jar", "zip", "txt");
        List<String> excludeDir = Arrays.asList("新建文件夹");
        List<String> filesPath = SearchWordUtils.getAllFilesPath(dir, filesType, excludeDir);
        System.out.println();
        filesPath.forEach(System.out::println);
    }

    @DisplayName("在某一文本文件中搜索字符串")
    @Test
    public void searWord() {
        String filePath = "D:\\迅雷下载\\server.log";
        String searchWord = "insertData";
        Map<Integer, String> map = SearchWordUtils.scanFile(filePath, searchWord);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("第" + entry.getKey() + "行:" + entry.getValue());
        }
    }

    @DisplayName("在文件夹下的所有指定类型的文本文件中搜索字符串")
    @Test
    public void searWord_speciFiles() {
        String dir = "D:\\迅雷下载\\";
        String searchStr = "insertData";
        List<String> fileType = Arrays.asList("txt", "log");
        Map<String, Map<Integer, String>> map = SearchWordUtils.searchFiles(dir, searchStr, fileType);
        for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
            System.out.println("文件路径： " + m.getKey());
            for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                System.out.println("第" + n.getKey() + "行：" + n.getValue());
            }
            System.out.println();
        }
    }

    @DisplayName("在文件夹下,除排除的文件夹外的所有指定类型的文本文件中搜索字符串")
    @Test
    public void searWord_exclude_speciFiles() {
        String dir = "D:\\迅雷下载\\";
        String searchStr = "insertData";
        List<String> fileType = Arrays.asList("txt", "log");
        List<String> excludeDir = Arrays.asList("新建文件夹"); //遇到文件夹名为"新建文件夹"则跳过该文件夹
        Map<String, Map<Integer, String>> map = SearchWordUtils.searchFiles(dir, searchStr, fileType, excludeDir);
        for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
            System.out.println("文件路径： " + m.getKey());
            for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                System.out.println("第" + n.getKey() + "行：" + n.getValue());
            }
            System.out.println();
        }
    }

    @DisplayName("在文件夹下的所有可读文件中搜索字符串")
    @Test
    public void searWordAllFilses() {
        String dir = "D:\\迅雷下载\\";
        String searchStr = "ssssss";
        Map<String, Map<Integer, String>> map = SearchWordUtils.searchAllFiles(dir, searchStr);
        System.out.println("");
        System.out.println("--------------------------------------------------------");
        System.out.println("搜索完成,结果：");
        for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
            System.out.println("文件路径： " + m.getKey());
            for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                System.out.println("第" + n.getKey() + "行：" + n.getValue());
            }
            System.out.println();
        }
    }


    @DisplayName("在指定文件夹下，除排除文件夹外的所有可读文件中搜索字符串")
    @Test
    public void searWordAllFilses_exclude() {
        String dir = "D:\\迅雷下载\\";
        String searchStr = "ssssss";
        List<String> excludeDir = Arrays.asList("laydate"); //遇到文件夹名为laydate"则跳过该文件夹
        Map<String, Map<Integer, String>> map = SearchWordUtils.searchAllFiles(dir, searchStr, excludeDir);
        System.out.println("");
        System.out.println("--------------------------------------------------------");
        System.out.println("搜索完成,结果：");
        for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
            System.out.println("文件路径： " + m.getKey());
            for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                System.out.println("第" + n.getKey() + "行：" + n.getValue());
            }
            System.out.println();
        }
    }

}
