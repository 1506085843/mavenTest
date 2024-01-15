package com.hai.tang.util;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DisplayName("excel读写测试类")
public class ExcelUtilsTest {

    public static String excelDir = "D:\\excelTest\\";
    public static String excelPath = "D:\\excelTest\\excelTest.xlsx";

    @BeforeAll
    public static void copyExcel() {
        try {
            //将 sec/test/resources/excel下的 excelTest.xlsx 复制到 D:\excelTest\ 下
            InputStream inputStream = ExcelUtilsTest.class.getClassLoader().getResourceAsStream("excel/excelTest.xlsx");
            if (null != inputStream) {
                FileUtils.copyInputStreamToFile(inputStream, new File(excelPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("读取excel文件中所有sheet工作表的数据")
    public void readAllSheet() {
        List<List<List<String>>> excelData = ExcelUtil.readAllSheet(excelPath);
        for (int i = 0; i < excelData.size(); i++) {
            System.out.println("excela中第" + i + "个sheet的内容：" + excelData.get(i));
        }
    }

    @Test
    @DisplayName("读取excel文件中第一个sheet工作表的数据")
    public void readFirstSheet() {
        List<List<String>> excelData = ExcelUtil.readFirstSheet(excelPath);
        System.out.println("excela中第1个sheet的内容：" + excelData);
    }

    @Test
    @DisplayName("读取excel文件中指定sheet工作表的数据")
    public void readIndexSheet() {
        List<List<String>> excelData = ExcelUtil.readSheet(excelPath, 1);
        System.out.println("excela中第2个sheet的内容：" + excelData);
    }

    @Test
    @DisplayName("读取excel文件中指定sheet工作表里指定列的数据")
    public void readColumSheet() {
        List<String> columData = ExcelUtil.readColum(excelPath, 1, 0);
        System.out.println("excela中第2个sheet里第1列的内容：：" + columData);
    }

    @Test
    @DisplayName("生成一个只有一个sheet的excel文件")
    public void writeExcel() {
        //第一行表头
        List<String> headerList = Stream.of("学校名称", "年级", "班级", "姓名", "性别", "年龄").collect(Collectors.toList());
        //数据
        List<List<String>> bodyList = new ArrayList<>();
        bodyList.add(Arrays.asList("南湖一中", "五年级", "1班", "张三", "男", "10"));
        bodyList.add(Arrays.asList("南湖二中", "四年级", "6班", "李四", "男", "9"));
        bodyList.add(Arrays.asList("汉阳一中", "三年级", "2班", "李梅", "女", "8"));
        bodyList.add(Arrays.asList("汉口二中", "六年级", "3班", "王飞", "男", "11"));
        ExcelUtil.exportExcel(excelDir, headerList, bodyList, "学生信息表1");
    }

    @Test
    @DisplayName("生成一个含有多个sheet的excel文件")
    public void writeMulExcel1() {
        List<List<String>> headerList = new ArrayList<>();
        headerList.add(Arrays.asList("学校名称", "年级", "班级", "姓名", "性别", "年龄"));
        headerList.add(Arrays.asList("学校", "年级", "人数"));

        List<List<List<String>>> bodyList = new ArrayList<>();
        List<List<String>> bodyList1 = new ArrayList<>();
        bodyList1.add(Arrays.asList("南湖一中", "五年级", "1班", "张三", "男", "10"));
        bodyList1.add(Arrays.asList("南湖二中", "四年级", "6班", "李四", "男", "9"));
        bodyList1.add(Arrays.asList("汉阳一中", "三年级", "2班", "李梅", "女", "8"));
        bodyList1.add(Arrays.asList("汉口二中", "六年级", "3班", "王飞", "男", "11"));
        bodyList.add(bodyList1);
        List<List<String>> bodyList2 = new ArrayList<>();
        bodyList2.add(Arrays.asList("云飞一中", "五年级", "500"));
        bodyList2.add(Arrays.asList("云飞二中", "四年级", "600"));
        bodyList2.add(Arrays.asList("云飞三中", "三年级", "100"));
        bodyList.add(bodyList2);
        ExcelUtil.exportMulExcel(excelDir, headerList, bodyList, "学习学生信息表2");
    }

    @Test
    @DisplayName("生成一个含有多个sheet的excel文件")
    public void writeMulExcel2() {
        List<List<String>> headerList = new ArrayList<>();
        headerList.add(Arrays.asList("学校名称", "年级", "班级", "姓名", "性别", "年龄"));
        headerList.add(Arrays.asList("学校", "年级", "人数"));

        List<List<List<String>>> bodyList = new ArrayList<>();
        List<List<String>> bodyList1 = new ArrayList<>();
        bodyList1.add(Arrays.asList("南湖一中", "五年级", "1班", "张三", "男", "10"));
        bodyList1.add(Arrays.asList("南湖二中", "四年级", "6班", "李四", "男", "9"));
        bodyList1.add(Arrays.asList("汉阳一中", "三年级", "2班", "李梅", "女", "8"));
        bodyList1.add(Arrays.asList("汉口二中", "六年级", "3班", "王飞", "男", "11"));
        bodyList.add(bodyList1);
        List<List<String>> bodyList2 = new ArrayList<>();
        bodyList2.add(Arrays.asList("云飞一中", "五年级", "500"));
        bodyList2.add(Arrays.asList("云飞二中", "四年级", "600"));
        bodyList2.add(Arrays.asList("云飞三中", "三年级", "100"));
        bodyList.add(bodyList2);
        //sheet名称
        List<String> sheetName = Arrays.asList("班级信息", "学校信息");
        ExcelUtil.exportMulExcel(excelDir, sheetName, headerList, bodyList, "学习学生信息表3");
    }
}
