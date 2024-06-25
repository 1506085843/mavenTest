package com.hai.tang.util;

import com.aspose.cells.HtmlSaveOptions;
import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.hai.tang.MainServer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * excel转换工具类，用于将 excel文件转化为 html或 pdf 格式的文件
 */
public class ExcelCoverUtils {
    /**
     * 获取 license 去除水印
     * 若不验证则转化出的pdf文档会有水印产生
     */
    public static void getLicense() {
        String licenseFilePath = "config/excel-license.xml";
        try {
            InputStream is = MainServer.class.getClassLoader().getResourceAsStream(licenseFilePath);
            License license = new License();
            license.setLicense(is);
        } catch (Exception e) {
            System.out.println("license verify failed");
            e.printStackTrace();
        }
    }

    /**
     * 隐藏workbook中不需要的sheet页。
     *
     * @param sheets 显示页的sheet数组
     */
    private static void printSheetPage(Workbook wb, int[] sheets) {
        for (int i = 1; i < wb.getWorksheets().getCount(); i++) {
            wb.getWorksheets().get(i).setVisible(false);
        }
        if (null == sheets || sheets.length == 0) {
            wb.getWorksheets().get(0).setVisible(true);
        } else {
            for (int i = 0; i < sheets.length; i++) {
                wb.getWorksheets().get(i).setVisible(true);
            }
        }
    }

    /**
     * 根据文件路径判断文件所在的文件夹是否存在，不存在则创建文件夹
     */
    private static void creatDir(String filePath) {
        Path path = Paths.get(filePath);
        //获取文件的父目录
        Path directoryPath = path.getParent();
        if (directoryPath != null) {
            try {
                if (Files.notExists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * excel文件转 html
     *
     * @param excelPath excel文件路径
     * @param htmlPath  要保存为的 html文件路径
     */
    public static void excelTohtml(String excelPath, String htmlPath) {
        getLicense();
        HtmlSaveOptions save = new HtmlSaveOptions(SaveFormat.HTML);
        //加载excel文件
        Workbook book = null;
        try {
            book = new Workbook(excelPath);
            creatDir(htmlPath);
            //转化为html
            book.save(htmlPath, save);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * excel文件转 pdf （默认将 excel里的所有 sheet 都转化）
     *
     * @param excelPath excel文件路径
     * @param pdfPath   要保存为的 pdf文件路径
     */
    public static void excelToPdf(String excelPath, String pdfPath) {
        excelToPdf(excelPath, pdfPath, null);
    }

    /**
     * excel文件转 pdf
     *
     * @param excelPath     excel文件路径
     * @param pdfPath       要保存为的 pdf文件路径
     * @param convertSheets excel文件里要转化的 sheet （sheet从0开始）
     */
    public static void excelToPdf(String excelPath, String pdfPath, int[] convertSheets) {
        getLicense();
        Workbook wb = null;
        try {
            wb = new Workbook(excelPath);
            creatDir(pdfPath);
            FileOutputStream fileOutputStream = new FileOutputStream(pdfPath);
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            if (null != convertSheets) {
                printSheetPage(wb, convertSheets);
            }
            wb.save(fileOutputStream, pdfSaveOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
