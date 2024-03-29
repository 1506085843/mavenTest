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

    @DisplayName("在文件夹下的所有指定类型的文本文件中搜索多个字符串，返回文件路径")
    @Test
    public void containSearchStrFiles() {
        String dir = "D:\\迅雷下载\\";
        List<String> searchStrList = Arrays.asList("张三", "李四");
        List<String> fileType = Arrays.asList("txt", "log");
        List<String> containFilePath = SearchWordUtils.containSearchStrFiles(dir, searchStrList, fileType);
        System.out.println();
        for (String filePath : containFilePath) {
            System.out.println("含有张三或李四的文件路径： " + filePath);
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

    @DisplayName("在指定文件夹下，根据某一文件名查询第一个索索到的文件的完整路径")
    @Test
    public void getFilePath() {
        //要查找的文件夹路径
        String dir = "D:\\GoogleDown\\";
        //查找文件名叫 xelem.xml 的文件，然后返回文件的路径。（即使不同文件夹有多个相同文件，找到第一个就立即返回，剩下的就不再继续找）
        String filesName = "xelem.xml";
        //开始查找
        String filesPath = SearchWordUtils.getFilesPath(dir, filesName);
        System.out.println(filesPath);
    }

    @DisplayName("在指定文件夹下，根据多个文件名查询返回文件的完整的文件路径")
    @Test
    public void getFilesPath() {
        //要查找的文件夹路径
        String dir = "D:\\GoogleDown\\";
        //查找文件名叫 guava-30.1.1-jre.jar 、 former.html 、xelem.xml 的文件，然后返回文件的路径
        List<String> filesName = Arrays.asList("guava-30.1.1-jre.jar", "former.html", "xelem.xml");
        //开始查找
        List<String> filesPath = SearchWordUtils.getFilesPath(dir, filesName);
        filesPath.forEach(System.out::println);
    }
}
