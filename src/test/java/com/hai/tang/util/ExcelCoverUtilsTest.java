package com.hai.tang.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("excel转html、pdf测试类")
public class ExcelCoverUtilsTest {

    @BeforeAll
    public static void getLicense() {
        //获取许可
        ExcelCoverUtils.getLicense();
    }

    @Test
    @DisplayName("excel文件转html")
    public void excelToHtml() {
        String excelPath = "C:\\Users\\haitang\\Downloads\\bbb.xlsx";
        String htmlPath = "C:\\Users\\haitang\\Downloads\\cover\\myExcel.html";
        ExcelCoverUtils.excelTohtml(excelPath, htmlPath);
    }

    @Test
    @DisplayName("excel文件转pdf")
    public void excelToPdf() {
        String excelPath = "C:\\Users\\haitang\\Downloads\\bbb.xlsx";
        String pdfPath = "C:\\Users\\haitang\\Downloads\\cover\\myExcel.pdf";
        ExcelCoverUtils.excelToPdf(excelPath, pdfPath);
    }

    @Test
    @DisplayName("excel文件转pdf，只转换第一个sheet")
    public void excelToPdf1() {
        String excelPath = "C:\\Users\\haitang\\Downloads\\bbb.xlsx";
        String pdfPath = "C:\\Users\\haitang\\Downloads\\cover\\myExcel.pdf";
        ExcelCoverUtils.excelToPdf(excelPath, pdfPath,new int[]{0});
    }
}
