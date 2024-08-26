package com.hai.tang.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ftp测试类")
public class FtpUtilsTest {
    private FakeFtpServer fakeFtpServer;

    private FtpUtils ftpClient;

    @BeforeEach
    public void setup()   {
        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        //模拟ftp文件夹及里面的文件和内容
        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fileSystem.add(new FileEntry("/data/foobar1.txt", "aaaaaa"));
        fileSystem.add(new FileEntry("/data/foobar2.sql", "bbb"));

        fileSystem.add(new DirectoryEntry("/root/usr"));
        fileSystem.add(new FileEntry("/root/usr/data/aa.txt", "abcdef 1234567890"));
        fileSystem.add(new FileEntry("/root/usr/data/bb.txt", "aaaaaa"));
        fileSystem.add(new FileEntry("/root/usr/data/cc.sql", "bbb"));

        fileSystem.add(new DirectoryEntry("/root/local/test"));
        fileSystem.add(new FileEntry("/root/local/data/ee.txt", "ee 1234567890"));
        fileSystem.add(new FileEntry("/root/local/data/ff.txt", "fff"));
        fileSystem.add(new FileEntry("/root/local/data/gg.sql", "ggg"));

        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();

        ftpClient = new FtpUtils("localhost", fakeFtpServer.getServerControlPort(), "user", "password");
        ftpClient.connect();
    }

    @AfterEach
    public void teardown()   {
        ftpClient.close();
        fakeFtpServer.stop();
    }

    @Test
    public void getFileList()  {
        List<String> files = ftpClient.filesList("/root/local/test");
        assertThat(files).contains("foobar.txt");
    }

    @Test
    public void downloadFile()  {
        ftpClient.downloadFile("/root/local/data/gg.sql", "C:\\Users\\haitang\\Downloads\\downloaded_buz.sql");
    }

    @Test
    public void uploadFile(){
        File file = new File("C:\\Users\\haitang\\Downloads\\qaz.sql");
        ftpClient.uploadFile("/root/local/test/buz.sql",file );
    }

    @Test
    public void deleteFile(){
        ftpClient.deleteFile("/root/local/data/ee.txt" );
        List<String> files1 = ftpClient.filesList("/root/local/data/");
        System.out.println(files1);
    }

    @Test
    public void createDir(){
        ftpClient.createDir("/xtxt/local/sx/" );
    }
}
