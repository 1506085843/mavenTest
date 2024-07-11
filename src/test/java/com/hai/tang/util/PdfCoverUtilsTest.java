package com.hai.tang.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("pdf转换测试类")
public class PdfCoverUtilsTest {

    @Test
    @DisplayName("pdf文件合并")
    public void pdfMerge() {
        //待合并的pdf文件
        String pdf1 = "C:\\Users\\haitang\\Downloads\\与神对话全集(pdf格式).pdf";
        String pdf2 = "C:\\Users\\haitang\\Downloads\\effective-java-2.pdf";
        List<String> pdfPaths = Arrays.asList(pdf1, pdf2);
        //合并为一个pdf
        PdfCoverUtils.pdfMergePdf(pdfPaths, "C:\\myCode\\mavenTest\\merge.pdf");
    }

    @Test
    @DisplayName("pdf提取文本内容到txt")
    public void pdfGetContentToTxt() {
        String pdf = "C:\\Users\\haitang\\Downloads\\effective-java-2.pdf";
        PdfCoverUtils.pdfGetContent(pdf,"C:\\myCode\\mavenTest\\pdfContent.txt");
    }

    @Test
    @DisplayName("pdf提取指定页数的文本内容到txt")
    public void pdfGetContentIndexToTxt() {
        String pdf = "C:\\Users\\haitang\\Downloads\\effective-java-2.pdf";
        PdfCoverUtils.pdfGetContentFromIndex(pdf,2,"C:\\myCode\\mavenTest\\pdfContentIndex.txt");
    }

    @Test
    @DisplayName("获取pdf的指定范围的页面为一个新的pdf")
    public void getNewPdfRange() {
        String pdf = "C:\\Users\\haitang\\Downloads\\effective-java-2.pdf";
        //获取第2到第3页为一个新的pdf
        PdfCoverUtils.getNewPdfRange(pdf,2,3,"C:\\myCode\\mavenTest\\newPdf.pdf");
    }

    @Test
    @DisplayName("pdf 转换为word 文档")
    public void convertPDFtoWord() {
        String pdf = "C:\\Users\\haitang\\Downloads\\effective-java-2.pdf";
        PdfCoverUtils.convertPDFtoWord(pdf,"C:\\myCode\\mavenTest\\word.doc");
    }
}
