package com.hai.tang.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    /**
     * 读取excel文件所有Sheet工作表里的数据 （支持xls、xlsx格式）
     *
     * @param file 文件路径，如：F:\\test.xls
     * @return List<List<List<String>>>  最外层的list是每一个sheet，中间的list是每行行，内层的list是每行里的数据
     */
    public static List<List<List<String>>> readAllSheet(String file) {
        Workbook workbook = getWorkbook(file);
        int numberOfSheets = workbook.getNumberOfSheets();//获取sheet个数
        List<List<List<String>>> list = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            list.add(readSheet(workbook, i));
        }
        return list;
    }

    /**
     * 读取 excel文件里第一个Sheet工作表里的数据 （支持xls、xlsx格式）
     *
     * @param file 文件路径，如：F:\\test.xls
     * @return List<List<String>>  最外层的list是每一行，内层的list是每列
     */
    public static List<List<String>> readFirstSheet(String file) {
        Workbook workbook = getWorkbook(file);
        return workbook != null ? readSheet(workbook, 0) : null;
    }

    /**
     * 读取 excel文件里指定Sheet工作表里的数据 （支持xls、xlsx格式）
     *
     * @param file        文件路径，如：F:\\test.xls
     * @param sheetNumber 要获取第几个sheet (sheetNumber从0开始数，即0表示第一个sheet)
     * @return List<List<String>>  最外层的list是每一行，内层的list是每列
     */
    public static List<List<String>> readSheet(String file, int sheetNumber) {
        Workbook workbook = getWorkbook(file);
        return workbook != null ? readSheet(workbook, sheetNumber) : null;
    }

    /**
     * 从excel中获取指定的列
     *
     * @param file        文件路径，如：F:\\test.xls
     * @param columnIndex 要获取的列的位置，如第1列则输入：1
     * @return List<String>  返回该列的内容
     */
    public static List<String> readColum(String file, int sheetIndex, int columnIndex) {
        Workbook workbook = getWorkbook(file);
        return workbook != null ? readColum(workbook, sheetIndex, columnIndex) : null;
    }


    private static List<String> readColum(Workbook workbook, int sheetIndex, int columnIndex) {
        int numberOfSheets = workbook.getNumberOfSheets();//获取sheet个数
        if (sheetIndex > numberOfSheets || columnIndex < 0) {
            return null;
        }
        // 取sheet页签
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        //遍历
        List<String> columList = new ArrayList<>();
        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            columList.add(cell.toString());
        }
        return columList;
    }

    /**
     * 生成excel文件 （只有一个sheet）
     *
     * @param header   excel的第一行标题
     * @param body     第二行开始的数据内容
     * @param fileName 要保存的文件名 , 最终保存到 F盘
     */
    public static void exportExcel(String outDir, List<String> header, List<List<String>> body, String fileName) {
        SXSSFWorkbook workBook = new SXSSFWorkbook();
        writeSheet(workBook, "sheet1", header, body);
        //生成excel
        generate(workBook, outDir, fileName);
    }

    /**
     * 生成excel文件 （含有多个sheet）
     *
     * @param outDir   excel输出目录
     * @param header   每一个sheet的表头
     * @param body     每一个sheet的表体
     * @param fileName 要保存的文件名
     */
    public static void exportMulExcel(String outDir, List<List<String>> header, List<List<List<String>>> body, String fileName) {
        exportMulExcel(outDir, null, header, body, fileName);
    }

    /**
     * 生成excel文件 （含有多个sheet）
     *
     * @param outDir     excel输出目录
     * @param sheetNames sheet名称
     * @param header     每一个sheet的表头
     * @param body       每一个sheet的表体
     * @param fileName   要保存的文件名
     */
    public static void exportMulExcel(String outDir, List<String> sheetNames, List<List<String>> header, List<List<List<String>>> body, String fileName) {
        SXSSFWorkbook workBook = new SXSSFWorkbook();
        if (null == header && null == body) {
            generate(workBook, outDir, fileName);
            return;
        }
        int sheetSize = 0;
        if (null == header) {
            sheetSize = body.size();
        } else if (null == body) {
            sheetSize = header.size();
        } else {
            sheetSize = Math.max(body.size(), header.size());
        }
        for (int i = 0; i < sheetSize; i++) {
            List<String> sheetHeader = null;
            List<List<String>> sheetBody = null;
            String sheetName = null;
            if (null != header && i < header.size()) {
                sheetHeader = header.get(i);
            }
            if (null != body && i < body.size()) {
                sheetBody = body.get(i);
            }
            if (null != sheetNames && i < sheetNames.size()) {
                sheetName = sheetNames.get(i);
            }
            writeSheet(workBook, sheetName, sheetHeader, sheetBody);
        }
        generate(workBook, outDir, fileName);
    }

    private static Workbook getWorkbook(String file) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    //读取第sheetIndex个sheet的数据
    private static List<List<String>> readSheet(Workbook workbook, int sheetIndex) {
        List<List<String>> sheetList = new ArrayList<>();
        int numberOfSheets = workbook.getNumberOfSheets();//获取sheet个数
        if (sheetIndex > numberOfSheets) {
            return null;
        }
        Sheet sheet = workbook.getSheetAt(sheetIndex);//获取sheet页签
        int rowNum = sheet.getLastRowNum();//得到该sheet的总行数
        for (int i = 0; i <= rowNum; i++) {
            Row row = sheet.getRow(i);//获取当前行
            List<String> rowList = new ArrayList<>();
            if (null == row) {
                sheetList.add(rowList);
                continue;
            }
            int colNum = row.getLastCellNum();//获取当前行最后的列
            for (int j = 0; j < colNum; j++) {
                Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                rowList.add(cell.toString());
            }
            sheetList.add(rowList);
        }
        return sheetList;
    }

    //生成excel
    private static void generate(SXSSFWorkbook workBook, String outDir, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outDir + fileName + ".xlsx");
            workBook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建一个sheet,并向其中写入表头和表体
    private static void writeSheet(SXSSFWorkbook workBook, String sheetName, List<String> header, List<List<String>> body) {
        SXSSFSheet sheet = null;
        if (null == sheetName) {
            int numberOfSheets = workBook.getNumberOfSheets() + 1;
            sheetName = "Sheet" + numberOfSheets;
        }
        sheet = workBook.createSheet(sheetName);
        if (null != header) {
            createHeader(header, workBook, sheet);
        }
        if (null != body) {
            createBody(body, workBook, sheet);
        }
    }

    //创建表头
    private static void createHeader(List<String> headers, Workbook workBook, Sheet sheet) {
        //sheet中创建第0行
        Row row = sheet.createRow(0);
        //设置单元格格式为居中
        CellStyle style = workBook.createCellStyle();
        //单元格文字居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置表头为绿色
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workBook.createFont();// 创建字体
        font.setColor(IndexedColors.WHITE.getIndex()); // 设置字体颜色为红色
        //设置表头文字为白色
        style.setFont(font);
        //遍历headers插入Excel第一行做表头
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(style);//设置单元格样式
        }
    }

    //创建表体
    private static void createBody(List<List<String>> body, Workbook workBook, Sheet sheet) {
        Row row;
        //int rowIndex = 0;
        int rowIndex = sheet.getLastRowNum();
        CellStyle style = workBook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//单元格样式内容居中
        for (int i = 0; i < body.size(); i++) {
            row = sheet.createRow(++rowIndex);
            List<String> each = body.get(i);
            Cell cell;
            for (int j = 0; j < each.size(); j++) {
                String cellValue = String.valueOf(each.get(j));
                cell = row.createCell(j);
                cell.setCellValue(cellValue);
                cell.setCellStyle(style);//设置单元格样式
            }
        }
    }
}

