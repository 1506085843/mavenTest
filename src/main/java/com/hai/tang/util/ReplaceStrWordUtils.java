package com.hai.tang.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于查找并替换字符串中字符串的工具类
 *
 * 本工具类不同于 ReplaceFileWordUtils，ReplaceFileWordUtils里的方法都会先读取文件再替换最后写入文件，如果操作的文件很多势必有很多读写操作会比较耗时，
 *
 */
public class ReplaceStrWordUtils {

    private String strWord;

    public ReplaceStrWordUtils(String strWord) {
        this.strWord = strWord;
    }

    /**
     * 字符串 strWord 中搜索第一次出现的 searchWord，并将其替换为 replaceWord
     * <p>
     * 如：ReplaceWordUtils.replaceWordFirst("经典散文经典文章大全1","李四哈哈");
     */
    public ReplaceStrWordUtils replaceWordFirst(String searchWord, String replaceWord) {
        replaceWord(searchWord, replaceWord, true);
        return this;
    }


    /**
     * 字符串 strWord 中搜索所有的 searchWord，并将其替换为 replaceWord
     * <p>
     * 如：ReplaceWordUtils.replaceWordAll("经典散文经典文章大全1","李四哈哈");
     */
    public ReplaceStrWordUtils replaceWordAll( String searchWord, String replaceWord) {
        replaceWord(searchWord, replaceWord, false);
        return this;
    }

    /**
     * 字符串 strWord 中搜索所有的 searchReplaceMap 的键，并将其替换为对应的值
     * <p>
     * 如：     Map<String,String> searchReplaceMap = new HashMap<>();
     * searchReplaceMap.put("《经典","aa");
     * searchReplaceMap.put("《优美","bb");
     * ReplaceWordUtils.replaceMultWordAll(searchReplaceMap);
     */
    public ReplaceStrWordUtils replaceMultWordAll(Map<String, String> searchReplaceMap) {
        if (null == strWord) {
            return this;
        }
        for (Map.Entry<String, String> m : searchReplaceMap.entrySet()) {
            String searchWord = m.getKey();
            String replaceWord = m.getValue();
            strWord = strWord.replaceAll(searchWord, replaceWord);
        }
        return this;
    }

    /**
     * 字符串 strWord 中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord xxxxxx searchEndWord替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxxxx1》 替换为 a李四哈哈
     * ReplaceWordUtils.replaceStartEnd("《经典","1》","a李四哈哈");
     */
    public ReplaceStrWordUtils replaceStartEnd(String searchStartWord, String searchEndWord, String replaceWord) {
        Map<String, String> startEndWordsMap = new HashMap<>();
        startEndWordsMap.put(searchStartWord, searchEndWord);
        replaceStartEnds(startEndWordsMap, replaceWord);
        return this;
    }

    /**
     * 字符串 strWord 中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将 键xxx值 替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxx1》、《优美xxx精选》 替换为 a李四哈哈
     * Map<String,String> startEndWordsMap = new HashMap<>();
     * startEndWordsMap.put("《经典","1》");
     * startEndWordsMap.put("《优美","精选》");
     * ReplaceWordUtils.replaceStartEnds(startEndWordsMap,"a李四哈哈");
     */
    public ReplaceStrWordUtils replaceStartEnds(Map<String, String> startEndWordsMap, String replaceWord) {
        if (null == strWord) {
            return this;
        }
        for (Map.Entry<String, String> m : startEndWordsMap.entrySet()) {
            String searchStartWord = m.getKey();
            String searchEndWord = m.getValue();
            replaceStartEndExecute(searchStartWord, searchEndWord, replaceWord);
        }
        return this;
    }

    /**
     * 字符串 strWord 中搜索所有的 searchStartWord xxxxxx searchEndWord，然后将searchStartWord 和 searchEndWord 之间的 xxx 替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxx1》替换为 《经典a李四哈哈1》
     * ReplaceWordUtils.replaceBetween("《经典","1》","a李四哈哈");
     */
    public ReplaceStrWordUtils replaceBetween(String searchStartWord, String searchEndWord, String replaceWord) {
        Map<String, String> startEndWordsMap = new HashMap<>();
        startEndWordsMap.put(searchStartWord, searchEndWord);
        replaceBetweens(startEndWordsMap, replaceWord);
        return this;
    }

    /**
     * 字符串 strWord 中搜索所有 startEndWordsMap 键值对之间的字符串xxx，将键值中间的 xxx 替换为 replaceWord
     * <p>
     * 如：将文本中的《经典xxx1》、《优美xxx精选》 分别替换为 《经典a李四哈哈1》、《优美a李四哈哈精选》
     * Map<String,String> map = new HashMap<>();
     * map.put("《经典","1》");
     * map.put("《优美","精选》");
     * ReplaceWordUtils.replaceBetweens(map,"a李四哈哈");
     */
    public ReplaceStrWordUtils replaceBetweens(Map<String, String> startEndWordsMap, String replaceWord) {
        if (null == strWord) {
            return this;
        }
        for (Map.Entry<String, String> m : startEndWordsMap.entrySet()) {
            String searchStartWord = m.getKey();
            String searchEndWord = m.getValue();
            String regex = searchStartWord + "(.*?)" + searchEndWord;
            strWord = strWord.replaceAll(regex, searchStartWord + replaceWord + searchEndWord);
        }
        return this;
    }

    //获取替换后的字符串
    public String get() {
        return strWord;
    }

    private String replaceWord(String searchWord, String replaceWord, boolean onlyReplaceFirst) {
        if (null == strWord) {
            return "";
        }
        strWord = onlyReplaceFirst ? strWord.replaceFirst(searchWord, replaceWord) : strWord.replaceAll(searchWord, replaceWord);
        return strWord;
    }

    private String replaceStartEndExecute(String start, String end, String replacement) {
        String regex = start + "(.*?)" + end;
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(strWord);
        if (matcher.find()) {
            strWord = matcher.replaceAll(replacement);
        }
        return strWord;
    }
}

