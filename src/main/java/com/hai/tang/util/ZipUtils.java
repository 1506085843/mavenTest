package com.hai.tang.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    /**
     * 文件夹压缩成ZIP
     *
     * @param srcDir           待压缩文件夹路径
     * @param zipPathName      压缩文件输出路径（含名称及后缀）
     * @param keepDirStructure 是否保留原来的目录结构：
     *                         true:保留文件目录结构;
     *                         false:不保留文件目录结构，把所有文件一起压缩，即提取文件夹及其所有子文件夹里的文件进行压缩，压缩后的压缩包里不含有文件夹
     *                         (注意：不保留文件目录结构可能会出现同名文件,会压缩失败。例如：子文件夹 a下有 a.txt,子文件夹 b下有 a.txt )
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, String zipPathName, boolean keepDirStructure) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(new File(zipPathName));
                BufferedOutputStream out = new BufferedOutputStream(fileOutputStream);
                ZipOutputStream zos = new ZipOutputStream(out);
        ) {
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
        } catch (Exception e) {
            throw new RuntimeException("压缩错误", e);
        }
    }


    /**
     * 多文件压缩成ZIP
     *
     * @param imageMap    需要压缩的文件列表，键值对为 <文件名，文件的字节数组>，文件名必须包含后缀
     * @param zipPathName 压缩文件输出路径（含名称及后缀）
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(Map<String, byte[]> imageMap, String zipPathName) throws RuntimeException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(new File(zipPathName));
                BufferedOutputStream out = new BufferedOutputStream(fileOutputStream);
                ZipOutputStream zos = new ZipOutputStream(out);
        ) {
            for (Map.Entry<String, byte[]> map : imageMap.entrySet()) {
                zos.putNextEntry(new ZipEntry(map.getKey()));
                zos.write(map.getValue());
                zos.closeEntry();
            }
        } catch (Exception e) {
            throw new RuntimeException("压缩错误", e);
        }
    }


    /**
     * 多文件压缩成ZIP
     *
     * @param srcFiles    需要压缩的文件列表
     * @param zipPathName 压缩文件输出路径（含名称及后缀）
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, String zipPathName) throws RuntimeException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(new File(zipPathName));
                BufferedOutputStream out = new BufferedOutputStream(fileOutputStream);
                ZipOutputStream zos = new ZipOutputStream(out);
        ) {
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[2048];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }
        } catch (Exception e) {
            throw new RuntimeException("压缩错误", e);
        }
    }


    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param keepDirStructure 是否保留原来的目录结构：
     *                         true:保留目录结构;
     *                         false:不保留文件目录结构，把所有文件一起压缩，即提取文件夹及其所有子文件夹里的文件进行压缩，压缩后的压缩包里不含有文件夹
     *                         (注意：不保留文件目录结构可能会出现同名文件,会压缩失败。例如：子文件夹 a下有 a.txt,子文件夹 b下有 a.txt )
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) {
        byte[] buf = new byte[2048];
        try {
            if (sourceFile.isFile()) {
                // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
                zos.putNextEntry(new ZipEntry(name));
                // copy文件到zip输出流中
                int len;
                FileInputStream in = new FileInputStream(sourceFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            } else {
                File[] listFiles = sourceFile.listFiles();
                if (listFiles == null || listFiles.length == 0) {
                    // 需要保留原来的文件结构时,需要对空文件夹进行处理
                    if (keepDirStructure) {
                        // 空文件夹的处理
                        zos.putNextEntry(new ZipEntry(name + "/"));
                        // 没有文件，不需要文件的copy
                        zos.closeEntry();
                    }
                } else {
                    for (File file : listFiles) {
                        // 判断是否需要保留原来的文件结构
                        if (keepDirStructure) {
                            // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                            // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                            compress(file, zos, name + "/" + file.getName(), keepDirStructure);
                        } else {
                            compress(file, zos, file.getName(), keepDirStructure);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("压缩错误", e);
        }
    }

    /**
     * zip格式的压缩文件解压
     *
     * @param zipFilePath 待解压的压缩文件
     * @param destDir     要解压到的文件夹
     */
    public static void unzip(String zipFilePath, String destDir) {
        try (
                FileInputStream fis = new FileInputStream(zipFilePath);
                ZipInputStream zis = new ZipInputStream(fis);
        ) {
            File destDirFile = new File(destDir);
            if (!destDirFile.exists()) {
                destDirFile.mkdirs(); // 确保目标目录存在
            }
            ZipEntry ze = zis.getNextEntry();
            byte[] buffer = new byte[1024];
            int length;
            while (ze != null) {
                File newFile = new File(destDir + File.separator + ze.getName());
                // 确保文件所在的目录存在
                newFile.getParentFile().mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                while ((length = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * InputStream 或 FileInputStream 转 byte[ ]
     *
     */
    public static byte[] toByteArray(InputStream input) {
        int nRead;
        byte[] data = new byte[16384];
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            while ((nRead = input.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[1];
    }

}

