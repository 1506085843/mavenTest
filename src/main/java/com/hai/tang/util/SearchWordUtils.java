package com.hai.tang.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SearchWordUtils {

    //当在文件夹中搜索时，遇到以下非文本文件则跳过
    public static List<String> excludeFileType = Arrays.asList(
            "jar", "zip", "rar", "7z", "tar", "gz", "xz", "bz2", "doc", "class", "pak",
            "xls", "ppt", "pdf", "docx", "xlsx", "pptx", "jpg", "jpge", "gif", "png",
            "xltd", "war", "hprof", "m4a", "swf", "mobi", "jpeg", "tiff", "svg", "psd",
            "mp3", "aac", "mp4", "avi", "flv", "mkv", "mpeg", "msi", "tgz", "mdf",
            "rmvb", "apk", "ts", "map", "car", "mov", "wav", "raw", "dll", "woff",
            "eot", "otf", "ico", "ttf", "ttc", "fon", "dl_", "pd_", "ex_", "etl",
            "sys", "iso", "isz", "esd", "wim", "gho", "dmg", "mpf", "exe", "ldf");

    /**
     * 搜索指定文件中的关键字
     *
     * @param filePath  要搜索的文件路径
     * @param searchStr 要搜索的关键字
     * @return 返回的 map<行数, 该行内容>
     */
    public static Map<Integer, String> scanFile(String filePath, String searchStr) {
        Map<Integer, String> map = new LinkedHashMap<>();
        FileInputStream file = null; //读取文件为字节流
        try {
            file = new FileInputStream(filePath);
            InputStreamReader in = new InputStreamReader(file, StandardCharsets.UTF_8); //字节流转化为字符流，以GBK读取防止中文乱码
            BufferedReader buf = new BufferedReader(in); //加入到缓存区
            String str = "";
            int row = 1;
            while ((str = buf.readLine()) != null) { //按行读取，到达最后一行返回null
                if (str.contains(searchStr)) {
                    map.put(row, str);
                }
                row++;
            }
            buf.close();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 扫描dirPath下所有文件
     *
     * @param dirPath 要搜索的文件夹路径
     * @return 返回所有文件的路径
     */
    public static List<String> getAllFilesPath(String dirPath) {
        List<String> list = new ArrayList<>();
        return getAll_FilesPath(dirPath, list);
    }

    /**
     * 扫描dirPath下所有文件
     *
     * @param dirPath    要搜索的文件夹路径
     * @param excludeDir 不扫描的文件夹名列表
     * @return 返回所有文件的路径
     */
    public static List<String> getAllFilesPathEx(String dirPath, List<String> excludeDir) {
        List<String> list = new ArrayList<>();
        return getAll_FilesPath(dirPath, list, excludeDir);
    }

    /**
     * 扫描dirPath下的所有文件类型是fileType的文件
     *
     * @param dirPath  要搜索的文件夹路径
     * @param fileType 文件后缀，要扫描的文件的类型
     * @return 返回所有fileType类型文件的路径
     */
    public static List<String> getAllFilesPath(String dirPath, List<String> fileType) {
        List<String> list = new ArrayList<>();
        return getAllFiles(dirPath, fileType, list);
    }

    /**
     * 扫描dirPath下的所有文件类型是fileType的文件
     *
     * @param dirPath    要搜索的文件夹路径
     * @param fileType   文件后缀，要扫描的文件的类型
     * @param excludeDir 不扫描的文件夹名列表
     * @return 返回所有fileType类型文件的路径
     */
    public static List<String> getAllFilesPath(String dirPath, List<String> fileType, List<String> excludeDir) {
        List<String> list = new ArrayList<>();
        return getAllFiles(dirPath, fileType, list, excludeDir);
    }

    /**
     * @param dirPath   要搜索的文件夹路径
     * @param searchStr 要搜索的关键字
     * @param fileType  要搜索的文件后缀
     * @return <文件名, <行数, 该行内容>>
     */
    public static Map<String, Map<Integer, String>> searchFiles(String dirPath, String searchStr, List<String> fileType) {
        return searchFiles(dirPath, searchStr, fileType, null);
    }

    /**
     * @param dirPath    要搜索的文件夹路径
     * @param searchStr  要搜索的关键字
     * @param fileType   要搜索的文件后缀
     * @param excludeDir 不扫描的文件夹名列表
     * @return <文件名, <行数, 该行内容>>
     */
    public static Map<String, Map<Integer, String>> searchFiles(String dirPath, String searchStr, List<String> fileType, List<String> excludeDir) {
        List<String> allFiles = excludeDir == null || excludeDir.size() == 0 ? getAllFilesPath(dirPath, fileType) : getAllFilesPath(dirPath, fileType, excludeDir);
        Map<String, Map<Integer, String>> searchInfo = new LinkedHashMap<>();
        for (String f : allFiles) {
            System.out.println("正在文件中搜索，当前搜索文件：" + f);
            Map<Integer, String> map = scanFile(f, searchStr);
            if (map.size() != 0) {
                searchInfo.put(f, map);
            }
        }
        return searchInfo;
    }


    /**
     * 搜索文件夹下所有可读文件中是否含有要查找的关键字
     *
     * @param dirPath   要搜索的文件夹路径
     * @param searchStr 要搜索的关键字
     * @return <文件名, <行数, 该行内容>>
     */
    public static Map<String, Map<Integer, String>> searchAllFiles(String dirPath, String searchStr) {
        return searchAllFiles(dirPath, searchStr, null);
    }

    /**
     * 搜索文件夹下所有可读文件中是否含有要查找的关键字
     *
     * @param dirPath    要搜索的文件夹路径
     * @param searchStr  要搜索的关键字
     * @param excludeDir 不扫描的文件夹名列表
     * @return <文件名, <行数, 该行内容>>
     */
    public static Map<String, Map<Integer, String>> searchAllFiles(String dirPath, String searchStr, List<String> excludeDir) {
        List<String> allFiles = excludeDir == null || excludeDir.size() == 0 ? getAllReadFilessPath(dirPath, new ArrayList<>(), null) : getAllReadFilessPath(dirPath, new ArrayList<>(), excludeDir);
        Map<String, Map<Integer, String>> searchInfo = new LinkedHashMap<>();
        for (String f : allFiles) {
            System.out.println("正在文件中搜索，当前搜索文件：" + f);
            Map<Integer, String> map = scanFile(f, searchStr);
            if (map.size() != 0) {
                searchInfo.put(f, map);
            }
        }
        return searchInfo;
    }

    /**
     * @param dirPath   要搜索的文件夹路径
     * @param searchStr 要搜索的关键字
     * @param fileType  要搜索的文件后缀
     * @return <文件名, <行数, 该行内容>>
     */
    public static void searchAndPrint(String dirPath, String searchStr, List<String> fileType) {
        Map<String, Map<Integer, String>> map = searchFiles(dirPath, searchStr, fileType);
        for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
            System.out.println("文件路径： " + m.getKey());
            for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                System.out.println("第" + n.getKey() + "行：" + n.getValue());
            }
            System.out.println();
        }
    }

    //获取所有的文件路径,excludeDir是要跳过查询的文件夹名列表
    public static List<String> getAllReadFilessPath(String dirPath, List<String> list, List<String> excludeDir) {
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        System.out.println("正在扫描文件夹：" + dirPath);
        if (null != tempList) {
            for (int i = 0; i < tempList.length; i++) {
                String filePath = tempList[i].toString();
                String file_Type = filePath.substring(filePath.lastIndexOf(".") + 1);
                if (tempList[i].isFile()) {
                    //如果是可读的文本文件
                    if (!excludeFileType.contains(file_Type)) {
                        list.add(filePath);
                    }
                } else {
                    if (excludeDir == null || excludeDir.size() == 0) {
                        getAllReadFilessPath(filePath, list, excludeDir);
                    } else {
                        String dirName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
                        if (!excludeDir.contains(dirName)) {
                            //如果是文件夹则递归
                            getAllReadFilessPath(filePath, list, excludeDir);
                        }
                    }
                }
            }
        }
        return list;
    }

    //获取文件夹下所有的文件路径,excludeDir是要跳过查询的文件夹名列表
    public static List<String> getAll_FilesPath(String dirPath, List<String> list, List<String> excludeDir) {
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        System.out.println("正在扫描文件夹：" + dirPath);
        if (null != tempList) {
            for (int i = 0; i < tempList.length; i++) {
                String filePath = tempList[i].toString();
                if (tempList[i].isFile()) {
                    list.add(filePath);
                } else {
                    //如果是文件夹则递归
                    if (excludeDir == null || excludeDir.size() == 0) {
                        getAll_FilesPath(filePath, list);
                    } else {
                        String dirName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
                        if (!excludeDir.contains(dirName)) {
                            //如果是文件夹则递归
                            getAll_FilesPath(filePath, list, excludeDir);
                        }
                    }
                }
            }
        }
        return list;
    }

    //获取文件夹下所有的文件路径
    public static List<String> getAll_FilesPath(String path, List<String> list) {
        return getAll_FilesPath(path, list, null);
    }

    //获取所有的文件路径,fileType是所有要查询的文件类型
    public static List<String> getAllFiles(String dirPath, List<String> fileType, List<String> list) {
        return getAllFiles(dirPath, fileType, list, null);
    }

    //获取所有的文件路径,fileType是所有要查询的文件类型,excludeDir是要跳过查询的文件夹名列表
    public static List<String> getAllFiles(String dirPath, List<String> fileType, List<String> list, List<String> excludeDir) {
        File file = new File(dirPath);
        File[] tempList = file.listFiles();
        System.out.println("正在扫描文件夹：" + dirPath);
        if (null != tempList) {
            for (int i = 0; i < tempList.length; i++) {
                String filePath = tempList[i].toString();
                String file_Type = filePath.substring(filePath.lastIndexOf(".") + 1);
                if (tempList[i].isFile()) {
                    //如果是文件
                    if (fileType.contains(file_Type)) {
                        list.add(filePath);
                    }
                } else {
                    if (excludeDir == null || excludeDir.size() == 0) {
                        getAllFiles(filePath, fileType, list);
                    } else {
                        String dirName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
                        if (!excludeDir.contains(dirName)) {
                            //如果是文件夹则递归
                            getAllFiles(filePath, fileType, list, excludeDir);
                        }
                    }
                }
            }
        }
        return list;
    }
}




