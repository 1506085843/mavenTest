package com.hai.tang.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于统计文本文件的文字数量的工具类
 */
public class StatisticsWordNumberUtils {
    static List<String> list = new ArrayList<>(Arrays.asList("，", ",", "？", "?", "：", ":", "。", "、", "！", "#", "“", "”", ";", "《", "》", "（", "）", "(", ")", "'", ".", "*", "@", "%", "&", "-", "_", "=", "[", "]", "{", "}", "/", "\\", "|", "~", "`", "$", "^", "+", "<", ">", "!", "￥", "【", "】"));

    public static int[] statisticsWord(String filePath) {
        byte[] data = new byte[0]; //获取文件转化为字节数组
        try {
            data = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = new String(data, StandardCharsets.UTF_8);
        int symble = 0;
        int chines = 0;
        for (String s : str.split("")) {
            if (s.trim().length() == 0) {
                continue;
            }
            if (list.contains(s)) {
                symble++;
                continue;
            }
            chines++;
        }
        int all = symble + chines;
        return new int[]{symble, chines, all};
    }

    /**
     * 统计某一文件的文字数量
     * 使用：StatisticsWordNumberUtils.statisticsFileWord("F:/时空之戒/第1章 最后一天.md");
     */
    public static void statisticsFileWord(String filePath) {
        int[] wordNumber = statisticsWord(filePath);
        System.out.println("文件名：" + filePath + "      符号数：" + wordNumber[0] + "  汉字数：" + wordNumber[1] + "  符号加汉字总字数：" + wordNumber[2]);
    }

    /**
     * 统计某一文件的文字数量
     * 使用：StatisticsWordNumberUtils.statisticsFilesWord("F:/时空之戒/");
     */
    public static void statisticsFilesWord(String filesPath) {
        Map<String, Object> map = getAllFilesPath(filesPath);
        List<String> allFilesPath = (List<String>) map.get("filesPathList");
        int maxLength = (int) map.get("maxLength");
        int symbleAll = 0;
        int chinesAll = 0;
        int symblechinesAll = 0;
        for (String filePath : allFilesPath) {
            int[] wordNumber = statisticsWord(filePath);
            if (filePath.length() < maxLength) {
                int diffLength = maxLength - filePath.length();
                StringBuilder build = new StringBuilder(" ");
                for (int i = 0; i < diffLength; i++) {
                    build.append("一");
                }
                filePath = filePath + build;
            }

            System.out.println("文件名：" + filePath + " 符号数：" + wordNumber[0] + "  汉字数：" + wordNumber[1] + "  符号加汉字总字数：" + wordNumber[2]);
            symbleAll = symbleAll + wordNumber[0];
            chinesAll = chinesAll + wordNumber[1];
        }
        symblechinesAll = symbleAll + chinesAll;
        System.out.println("总计：" + " 符号数：" + symbleAll + "  汉字数：" + chinesAll + "  符号加汉字总字数：" + symblechinesAll);
    }

    //获取文件夹下所有的文件路径
    public static Map<String, Object> getAllFilesPath(String dirPath) {
        Map<String, Object> map = new HashMap<>();
        List<String> filesPathList = new ArrayList<>();
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        if (tempList == null) {
            return map;
        }
        int maxLength = 0;
        for (File value : tempList) {
            if (value.isFile()) {
                String filePath = value.toString();
                filesPathList.add(filePath);
                if (filePath.length() > maxLength) {
                    maxLength = filePath.length();
                }
            }
        }
        filesPathList.sort(Comparator.comparingInt(StatisticsWordNumberUtils::extractNumber));
        map.put("filesPathList", filesPathList);
        map.put("maxLength", maxLength);
        return map;
    }

    //对文件名按照章节数字大小进行排序
    private static int extractNumber(String s) {
        try {
            String number = s.split("章")[0].replaceAll("[^0-9]", "");
            return Integer.parseInt(number);
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

}
