package com.hai.tang.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于查找并替换文本文件中字符串的工具类
 */
public class ReplaceWordUtils {

    /**
     * 在文本文件 filePath 中搜索第一次出现的 searchWord，并将其替换为 replaceWord
     * <p>
     * 如：ReplaceWordUtils.replaceWordFirst(filePath,"经典散文经典文章大全1","李四哈哈");
     */
    public static void replaceWordFirst(String filePath, String searchWord, String replaceWord) {
        replaceWord(filePath, searchWord, replaceWord, true);
    }

    /**
     * 在文本文件 filePath 中搜索所有的 searchWord，并将其替换为 replaceWord
     * <p>
     * 如：ReplaceWordUtils.replaceWordAll(filePath,"经典散文经典文章大全1","李四哈哈");
     */
    public static void replaceWordAll(String filePath, String searchWord, String replaceWord) {
        replaceWord(filePath, searchWord, replaceWord, false);
    }

    /**
     * 在文本文件 filePath 中搜索所有的 searchReplaceMap 的键，并将其替换为对应的值
     * <p>
     * 如：     Map<String,String> searchReplaceMap = new HashMap<>();
     *         searchReplaceMap.put("《经典","aa");
     *         searchReplaceMap.put("《优美","bb");
     *         ReplaceWordUtils.replaceMultWordAll(filePath,searchReplaceMap);
     */
    public static void replaceMultWordAll(String filePath, Map<String,String> searchReplaceMap) {
        String content = getFileContent(filePath);
        if (null == content) {
            return;
        }
        for (Map.Entry<String, String> m : searchReplaceMap.entrySet()) {
            String searchWord = m.getKey();
            String replaceWord = m.getValue();
            content = content.replaceAll(searchWord, replaceWord);
        }
        writeFileContent(filePath,content);
    }

    /**
     * 在文本文件 filePath 中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord xxxxxx searchEndWord替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxxxx1》 替换为 a李四哈哈
     *    ReplaceWordUtils.replaceStartEnd(filePath,"《经典","1》","a李四哈哈");
     */
    public static void replaceStartEnd(String filePath, String searchStartWord, String searchEndWord, String replaceWord) {
        Map<String, String> startEndWordsMap = new HashMap<>();
        startEndWordsMap.put(searchStartWord, searchEndWord);
        replaceStartEnds(filePath, startEndWordsMap, replaceWord);
    }

    /**
     * 在文本文件 filePath 中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将 键xxx值 替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxx1》、《优美xxx精选》 替换为 a李四哈哈
     *         Map<String,String> startEndWordsMap = new HashMap<>();
     *         startEndWordsMap.put("《经典","1》");
     *         startEndWordsMap.put("《优美","精选》");
     *         ReplaceWordUtils.replaceStartEnds(filePath,startEndWordsMap,"a李四哈哈");
     */
    public static void replaceStartEnds(String filePath, Map<String, String> startEndWordsMap, String replaceWord) {
        String content = getFileContent(filePath);
        if (null == content) {
            return;
        }
        for (Map.Entry<String, String> m : startEndWordsMap.entrySet()) {
            String searchStartWord = m.getKey();
            String searchEndWord = m.getValue();
            content = replaceStartEndExecute(content, searchStartWord, searchEndWord, replaceWord);
        }
        writeFileContent(filePath,content);
    }

    /**
     * 在文本文件 filePath 中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord 和 searchEndWord 之间的 xxx 替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxx1》替换为 《经典a李四哈哈1》
     *    ReplaceWordUtils.replaceBetween(filePath,"《经典","1》","a李四哈哈");
     */
    public static void replaceBetween(String filePath, String searchStartWord, String searchEndWord, String replaceWord) {
        Map<String, String> startEndWordsMap = new HashMap<>();
        startEndWordsMap.put(searchStartWord, searchEndWord);
        replaceBetweens(filePath, startEndWordsMap, replaceWord);
    }

    /**
     * 在文本文件 filePath 中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将键值中间的 xxx 替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxx1》、《优美xxx精选》 分别替换为 《经典a李四哈哈1》、《优美a李四哈哈精选》
     *    Map<String,String> map = new HashMap<>();
     *    map.put("《经典","1》");
     *    map.put("《优美","精选》");
     *    ReplaceWordUtils.replaceBetweens(filePath,map,"a李四哈哈");
     */
    public static void replaceBetweens(String filePath, Map<String, String> startEndWordsMap, String replaceWord) {
        String content = getFileContent(filePath);
        if (null == content) {
            return;
        }
        for (Map.Entry<String, String> m : startEndWordsMap.entrySet()) {
            String searchStartWord = m.getKey();
            String searchEndWord = m.getValue();
            String regex = searchStartWord + "(.*?)" + searchEndWord;
            content = content.replaceAll(regex, searchStartWord + replaceWord + searchEndWord);
        }
        writeFileContent(filePath,content);
    }

    //获取文本文件内容为一个字符串
    private static String getFileContent(String filePath) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    //将字符串写入文本文件
    private static void writeFileContent(String filePath,String content) {
        File file = new File(filePath);
        try {
            Writer  writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), 	StandardCharsets.UTF_8));
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceWord(String filePath, String searchWord, String replaceWord, boolean onlyReplaceFirst) {
        String content = getFileContent(filePath);
        if (null == content) {return;}
        content = onlyReplaceFirst ? content.replaceFirst(searchWord, replaceWord) : content.replaceAll(searchWord, replaceWord);
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replaceStartEndExecute(String original, String start, String end, String replacement) {
        String regex = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(original);
        if (matcher.find()) {
            return matcher.replaceAll(replacement);
        }
        return original;
    }
}
