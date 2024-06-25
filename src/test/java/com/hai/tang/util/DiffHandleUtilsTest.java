package com.hai.tang.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


@DisplayName("文本对比测试类")
public class DiffHandleUtilsTest {

    @DisplayName("对比两文件并生成html")
    @Test
    public void diffGenerateHtml() {
        //要对比的两文件路径
        String file1Path = "C:\\diff\\c1.txt";
        String file2Path = "C:\\diff\\c2.txt";
        //生成的html文件夹路径
        String resultDirPath = "C:\\diff\\";

        //对比两文件，获得不同点
        List<String> diffString = DiffHandleUtils.diffString(file1Path, file2Path);
        //在 D:\diff\ 目录下生成一个 diff.html 文件，打开便可看到两个文件的对比
        DiffHandleUtils.generateDiffHtml(diffString, resultDirPath);
        //把所需的 js和 css 从 resource 资源目录复制到 resultDirPath 目录下
        DiffHandleUtils.copyfile(resultDirPath);

        System.out.println("对比完成，请打开 " + resultDirPath + "diff.html 查看");
    }
}
