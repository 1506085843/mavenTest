package com.hai.tang.util;

import com.aspose.pdf.Document;
import com.aspose.pdf.Page;
import com.aspose.pdf.SaveFormat;
import com.aspose.pdf.TextAbsorber;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PdfCoverUtils {


    /**
     * 多个 pdf文件合并
     *
     * @param pdfPaths      要合并的多个 pdf 文件路径
     * @param mergeSavePath 最终合并完成输出的pdf文件路径
     */
    public static void pdfMergePdf(List<String> pdfPaths, String mergeSavePath) {
        try {
            Document mergeDocument = new Document();
            for (String pdfPath : pdfPaths) {
                Document document = new Document(new FileInputStream(pdfPath));
                mergeDocument.getPages().add(document.getPages());
            }
            mergeDocument.save(mergeSavePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * pdf文件提取所有页面文字内容到 txt 里
     *
     * @param pdfPath    pdf 文件路径
     * @param outTxtPath 最终输出的 txt文件路径
     */
    public static void pdfGetContent(String pdfPath, String outTxtPath) {
        Document pdfDocument = new Document(pdfPath);
        TextAbsorber textAbsorber = new TextAbsorber();
        pdfDocument.getPages().accept(textAbsorber);
        //提取到的pdf文本字符串
        String extractedText = textAbsorber.getText();
        try {
            //字符串写入txt
            FileWriter writer = new FileWriter(outTxtPath);
            writer.write(extractedText);
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * pdf文件提取所有页面文字内容到 txt 里
     *
     * @param pdfPath    pdf 文件路径
     * @param pageIndex  要提取的页码，（页码从 1开始）
     * @param outTxtPath 最终输出的 txt文件路径
     */
    public static void pdfGetContentFromIndex(String pdfPath, int pageIndex, String outTxtPath) {
        Document doc = new Document(pdfPath);
        TextAbsorber absorber = new TextAbsorber();
        absorber.getTextSearchOptions().setLimitToPageBounds(true);
        doc.getPages().get_Item(pageIndex).accept(absorber);
        // 提取到的pdf指定页面的文本字符串
        String extractedText = absorber.getText();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outTxtPath));
            writer.write(extractedText);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取pdf的指定范围的页面为一个新的pdf
     *
     * @param pdfPath    pdf 文件路径
     * @param start  开始页码，（页码从 1开始）
     * @param end  结束页码，（页码从 1开始）
     * @param outPdfPath 最终输出的 txt文件路径
     */
    public static void getNewPdfRange(String pdfPath, int start, int end, String outPdfPath) {
        Document pdfDocument = new Document(pdfPath);
        int pageCount = 1;
        Document newDocument = new Document();
        for (Page pdfPage : pdfDocument.getPages()) {
            if (start <= pageCount && end >= pageCount) {
                newDocument.getPages().add(pdfPage);
            }
            pageCount++;
        }
        newDocument.save(outPdfPath);
    }

    /**
     * pdf 转换为 word 文档
     *
     * @param pdfPath    pdf 文件路径
     * @param outDocxPath 最终输出的 docx文件路径
     */
    public static void convertPDFtoWord(String pdfPath, String outDocxPath) {
        Document document = new Document(pdfPath);
        document.save(outDocxPath, SaveFormat.DocX);
        document.close();
    }

}
