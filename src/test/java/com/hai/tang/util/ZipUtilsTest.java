package com.hai.tang.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("zip压缩解压测试类")
public class ZipUtilsTest {
    @DisplayName("文件夹压缩为zip")
    @Test
    public void zipDir() {
        //将 zipTest 文件夹压缩到 C:\myCode\zipTestOut\zipTest.zip
        ZipUtils.toZip("C:\\myCode\\zipTest", "C:\\myCode\\zipTestOut\\zipTest.zip", true);
    }

    @DisplayName("zip解压缩")
    @Test
    public void unZip() {
        //将 zipTest.zip 压缩包解压到 C:\myCode\zipTestOut 下
        ZipUtils.unzip("C:\\myCode\\zipTestOut\\zipTest.zip", "C:\\myCode\\zipTestOut");
    }

    @DisplayName("多文件压缩为zip")
    @Test
    public void zipFiles() {
        //要压缩的文件列表
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("C:\\myCode\\zipTest\\金证sql表字段逗号拼接临时文件.txt"));
        fileList.add(new File("C:\\myCode\\zipTest\\pom.xml"));
        //开始压缩到C:\myCode\zipTestOut\zipList.zip
        ZipUtils.toZip(fileList, "C:\\myCode\\zipTestOut\\zipList.zip");
    }

    @DisplayName("输入流压缩为zip")
    @Test
    public void zipInputStream() throws FileNotFoundException {
        //读取图片转换为byte[]
        FileInputStream inputStream1 = new FileInputStream("C:\\myCode\\zipTest\\aa.jpg");
        byte[] bytes1 = ZipUtils.toByteArray(inputStream1);
        //读取txtf转换为byte[]
        FileInputStream inputStream2 = new FileInputStream("C:\\myCode\\zipTest\\sql表字段逗号拼接临时文件.txt");
        byte[] bytes2 = ZipUtils.toByteArray(inputStream2);
        //读取jar转换为byte[]
        FileInputStream inputStream3 = new FileInputStream("C:\\myCode\\zipTest\\kone-rbac-2.3.2.6.jar");
        byte[] bytes3 = ZipUtils.toByteArray(inputStream3);

        //三个文件存入map
        Map<String, byte[]> map = new HashMap<>();
        map.put("aa.jpg", bytes1);
        map.put("金证sql表字段逗号拼接临时文件.txt", bytes2);
        map.put("kone-rbac-2.3.2.6.jar", bytes3);

        //将 上面三个文件 压缩到 C:\myCode\MyzipTest.zip
        ZipUtils.toZip(map, "C:\\myCode\\zipTestOut\\MyzipTest.zip");
    }
}
