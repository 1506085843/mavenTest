package com.hai.tang.util;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@DisplayName("sftp链接服务器测试类")
public class SftpUtilsTest {

    @DisplayName("文件夹是否存在")
    @Test
    public void dirIsExist() {
        //开启连接
        SftpUtils sftpUtil = new SftpUtils("root", "46sdffhg", "127.0.0.1", "22");
        sftpUtil.connect();
        //判断目录是否存在
        boolean dirBoolean = sftpUtil.isDirExist("/usr/local/aaa/");
        //关闭连接
        sftpUtil.close();
    }

    @DisplayName("创建文件夹")
    @Test
    public void createDir() {
        //开启连接
        SftpUtils sftpUtil = new SftpUtils("root", "46sdffhg", "127.0.0.1", "22");
        sftpUtil.connect();
        //创建一个文件夹
        sftpUtil.createDir("/usr/local/aaa/bbb/");
        //关闭连接
        sftpUtil.close();
    }

    @DisplayName("删除指定文件")
    @Test
    public void deleteFile() {
        //开启连接
        SftpUtils sftpUtil = new SftpUtils("root", "46sdffhg", "127.0.0.1", "22");
        sftpUtil.connect();
        //删除指定文件
        sftpUtil.deleteFile("/usr/local/aaa/install.txt");
        //关闭连接
        sftpUtil.close();
    }

    @DisplayName("文件上传到服务器上")
    @Test
    public void uploadFile() {
        //开启连接
        SftpUtils sftpUtil = new SftpUtils("root", "46sdffhg", "127.0.0.1", "22");
        sftpUtil.connect();
        //把本地的文件上传到服务器上
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File("D:\\GoogleDown\\myvideo.mp4"));
            sftpUtil.uploadFile(in, "/usr/local/aaa/", "myvideo.mp4");
            in.close();
            //关闭连接
            sftpUtil.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("从服务器上下载文件")
    @Test
    public void downloadFile() {
        //开启连接
        SftpUtils sftpUtil = new SftpUtils("root", "46sdffhg", "127.0.0.1", "22");
        sftpUtil.connect();
        //从服务器下载文件
        InputStream download = sftpUtil.downloadFile("/usr/local/aaa/", "myvideo.mp4");
        //把文件保存到本地
        File file = new File("D:\\myvideo.mp4");
        try {
            FileUtils.copyInputStreamToFile(download, file);
            download.close();
            //关闭连接
            sftpUtil.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("执行Linux命令")
    @Test
    public void excutingOrder() {
        //开启连接
        SftpUtils sftpUtil = new SftpUtils("root", "46sdffhg", "127.0.0.1", "22");
        sftpUtil.connect();
        //打印/usr/local/aaa/目录下的所有文件信息
        String result = sftpUtil.excutOrder("ls -a -l /usr/local/aaa/");
        System.out.println(result);
        //关闭连接
        sftpUtil.close();
    }
}
