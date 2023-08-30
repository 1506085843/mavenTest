package com.hai.tang.commonoperat;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@DisplayName("IO文件读测试类")
public class IOFileReadWriteTest {


    @Test
    @DisplayName("从控制台输入")
    public void inputConSole() {
        Scanner scanner = new Scanner(System.in);
        String inputStr = null;
        while ((inputStr = scanner.next()) != null) {
            System.out.println(inputStr);
        }

        //下面注释的代码使用nextLine()，其以Enter为结束符,也就是说 nextLine()方法返回的是输入回车之前的所有字符;nextLine()可以获得空格。
      /* Scanner scanner = new Scanner(System.in);
        String inputStr = null;  //输入字符串
        if (scanner.hasNextLine()) {
            String str2 = scanner.nextLine();
            System.out.println(str2);
        }
        scanner.close();
        */
    }

    @Test
    @DisplayName("判断文件、文件夹是否存在以及创建")
    public void isExistsAndCreat() throws IOException {
        //判断文件夹是否存在，不存在则创建
        String dirPth = "D:/ioTest/aa/bb";
        File folder = new File(dirPth);
        //判断bb文件夹是否存在
        if (!folder.exists() && !folder.isDirectory()) {
            System.out.println("文件夹不存在");
            boolean mkdirs = folder.mkdirs();//如果test下连aa文件夹也没有，会连同aa一起创建
            if (mkdirs) {
                System.out.println("创建文件夹成功");
            }
        } else {
            System.out.println("文件夹已存在");
        }

        //判断文件是否存在，不存在则创建
        String filePth = "D:/ioTest/test2.txt";
        File file = new File(filePth);
        if (!file.exists()) {
            System.out.println("文件不存在");
            //创建文件,如果ioTest文件夹不存在会报错，所有要先检查文件前的文件夹路径是否存在，不存在要先创建文件夹，最后再创建文件
            boolean createFile = file.createNewFile();
            if (createFile) {
                System.out.println("创建文件成功");
            }
        } else {
            System.out.println("文件存在");
        }
    }

    @Test
    @DisplayName("判断文件夹下有多少个文件和文件夹")
    public void countSubFilesDir() {
        String path = "D:\\ioTest\\";
        int fileCount = 0;
        int folderCount = 0;
        File d = new File(path);
        File list[] = d.listFiles();
        for (int i = 0; i < list.length; i++) {
            if (list[i].isFile()) {
                fileCount++;
            } else {
                folderCount++;
            }
        }
        System.out.println("文件个数：" + fileCount);
        System.out.println("文件夹数：" + folderCount);
    }


    //获取文件夹下所有的文件路径
    public static List<String> getAllFilesPath(String path, List<String> list) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            String filePath = tempList[i].toString();
            if (tempList[i].isFile()) {
                list.add(filePath);
            } else {
                //如果是文件夹则递归
                getAllFilesPath(filePath, list);
            }
        }
        return list;
    }

    @Test
    @DisplayName("遍历获取文件夹下所有文件的路径和名称")
    public void getAllFilesPath() {
        String dir = "D:\\迅雷下载\\";
        List<String> result = getAllFilesPath(dir, new ArrayList<>());
        for (String file : result) {
            System.out.println(file);
        }
    }

    @Test
    @DisplayName("输入流转化为byte数组")
    public void inputStreamToByte() throws IOException {
        FileInputStream in = new FileInputStream("F:/test/te.txt");//输入流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {   //输入流转读取到字节数组输出流
            bos.write(buffer, 0, len);
        }
        in.close();
        bos.flush();
        byte[] result = bos.toByteArray(); //输出流转化成byte数组
    }

    @Test
    @DisplayName("使用字节流读文件")
    public void readFile() throws IOException {
        FileInputStream file = new FileInputStream("F:/test/te.txt"); //读取文件为字节流
        BufferedInputStream buf = new BufferedInputStream(file); //加入缓存区
        int len = 0;
        byte[] bys = new byte[1024];
        while ((len = buf.read(bys)) != -1) {    //读取逐个字节数据到字节数组，读到末尾没数据了返回-1
            System.out.println(new String(bys, 0, len, StandardCharsets.UTF_8)); //通过new String将字节数组转化为UTF_8编码的字符串输出
        }
        file.close(); //关闭流
        buf.close();
    }

    @Test
    @DisplayName("使用字节流写文件")
    public void writeFile() throws IOException {
        //true代表追加模式写入，没有参数true文件的内容会以覆盖形式写
        FileOutputStream outputStream = new FileOutputStream("F:/test/te.txt", true);
        BufferedOutputStream buf = new BufferedOutputStream(outputStream); //加入缓存区

        buf.write("hello".getBytes()); //hello转化为字节数组写入文件
        buf.write("\n".getBytes()); //换行
        buf.write(97); // 97代表字母a,将a写入文件
        buf.write("\n".getBytes()); //换行
        byte[] bys = {97, 98, 99, 100, 101};
        buf.write(bys, 1, 3);//将98, 99, 100代表的bcd写入文件

        buf.close();  //关闭流
        outputStream.close();
    }

    @Test
    @DisplayName("优雅读文本为一个字符串")
    public void readFileToStr() throws IOException {
        //Files.readAllBytes(Path)方法把整个文件读入内存，此方法返回一个字节数组。在针对大文件的读取的时候，可能会出现内存不 足，导致堆溢出。所以大文件不推荐此方法读取。
        byte[] data = Files.readAllBytes(Paths.get("F:/oracle_init.sql")); //获取文件转化为字节数组
        String str = new String(data, StandardCharsets.UTF_8); //字节数组转化为UTF_8编码的字符串，若有中文乱码，可改用"GBK"编码
        System.out.println(str); //输出文件内容
    }

    @Test
    @DisplayName("优雅读文本每行为一个List")
    public void readFileToList() throws IOException {
        //readAllLines默认使用utf-8格式读取
        List<String> lines = Files.readAllLines(Paths.get("F:/zzz.txt"));
        lines.forEach(System.out::println);

        //有时候你的txt或者文档是GBK编码的读取就会报错：java.nio.charset.MalformedInputException: Input length = 1
        //List<String> lines = Files.readAllLines(Paths.get("F:/zzz.txt") , Charset.forName("GBK"));
        //lines.forEach(System.out::println);
    }

    @Test
    @DisplayName("优雅的将多行数据写入文本")
    public void writeToFile() throws IOException {
        Path out = Paths.get("D:\\GoogleDown\\test.txt");//文件不存在会自动创建
        List<String> arrayList = new ArrayList<>(Arrays.asList("a", "b", "c"));//abc分别是三行的内容
        Files.write(out, arrayList, Charset.defaultCharset());
    }

    @Test
    @DisplayName("java8优雅的写入文件")
    public void writeToFilejava8() throws IOException {
        //写文本文件文件不存在会自动创建，true表示在文件末尾追加形式写入，false则覆盖写入
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("F:/ccc.txt", true)));
        writer.println("777");
        writer.println("888");
        writer.close();

        //二进制文件（图片、音频等）
       /* byte data[] = ...
        FileOutputStream out = new FileOutputStream("F:/ccc.mp3");
        out.write(data);
        out.close();*/
    }

    @Test
    @DisplayName("使用字节流复制文件件")
    public void copyFile() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("F:/test/lv.mp4"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("F:/new/lv1.mp4"));

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = bis.read(bytes)) != -1) {
            bos.write(bytes, 0, len);
        }
        bis.close();
        bos.close();
    }

    @Test
    @DisplayName("java8使用Files.copy()复制文件")
    public void copyFilejsva8() throws IOException {
        Path source = Paths.get("F:/oracle_init.sql");   //源文件路径
        File f = new File("F:/test/new.sql");  //拷贝后的文件名和路径
        boolean newFile = f.createNewFile();//创建一个空的new.sql， 如果test目录下已经存在new.sql原来的会被覆盖。
        if (newFile) {
            //复制文件
            Files.copy(source, new FileOutputStream(f));
        }
    }

    @Test
    @DisplayName("InputStream、FileInputStream 转 byte[ ]")
    public void streamToByte() throws IOException {
        InputStream is = new FileInputStream("F:/test/te.txt");//输入流;
        //FileInputStream  is =new FileInputStream("F:/test/te.txt");//输入流;
        //利用Apache Commons IO库
        byte[] bytes = IOUtils.toByteArray(is);
    }

    @Test
    @DisplayName("文本文件的InputStream、FileInputStream 转 String")
    public void streamToStr() throws IOException {
        InputStream inputStream = new FileInputStream("F:/test/te.txt");//输入流;
        StringWriter writer = new StringWriter();
        //利用Apache Commons IO库
        IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8);
        String theString = writer.toString();
    }

    @Test
    @DisplayName("文件File转byte[]")
    public void fileToByte() throws IOException {
        File file = new File("F:/oracle_init.sql");
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int) file.length()];
        fl.read(arr);
        fl.close();

        //或者 利用Apache Commons IO库
        //File file = new File("F:/oracle_init.sql");
        //byte[] bytes = FileUtils.readFileToByteArray(file);
    }

    @Test
    @DisplayName("InputStream转文件")
    public void InputStreamWriteToFile() throws IOException {
        //获取 F:/test/test1.txt 文件为输入流，写入 F:/test2.txt
        InputStream in = new FileInputStream("F:/test/test1.txt");//输入流;
        FileOutputStream out = new FileOutputStream("F:/test2.txt");
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.close();

        //或者利用Apache Commons IO库：
       /* InputStream in = new FileInputStream("F:/test/test1.txt");//输入流;
        File file = new File("F:/test2.txt");
        FileUtils.copyInputStreamToFile(in, file);*/
    }

    @Test
    @DisplayName("byte[]转文件")
    public void biteToFile() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("F:/oracle_init.sql")); //获取文件转化为字节数组
        try (FileOutputStream fos = new FileOutputStream("F:/test1.txt")) {
            fos.write(bytes);
        }
    }

}
