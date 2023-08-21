package com.hai.tang.util;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public class SftpUtils {
    private static JSch jsch;
    private static Session session = null;
    private static Channel channel = null;
    private static ChannelSftp channelSftp = null;

    //服务器用户名
    private String ftpUserName;

    //服务器密码
    private String ftpPassword;

    //服务器ip
    private String ftpHost;

    //服务器端口
    private String ftpPort;

    public SftpUtils() {
    }

    public SftpUtils(String ftpUserName, String ftpPassword, String ftpHost, String ftpPort) {
        this.ftpUserName = ftpUserName;
        this.ftpPassword = ftpPassword;
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
    }

    /**
     * 开启连接
     */
    public ChannelSftp connect() {
        jsch = new JSch();
        try {
            // 根据用户名、主机ip、端口号获取一个Session对象
            session = jsch.getSession(ftpUserName, ftpHost, Integer.valueOf(ftpPort));
            // 设置密码
            session.setPassword(ftpPassword);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            // 为Session对象设置properties
            session.setConfig(config);
            // 设置连接超时为5秒
            session.setTimeout(100 * 50);
            // 通过Session建立连接
            session.connect();
            // 打开SFTP通道
            channel = session.openChannel("sftp");
            // 建立SFTP通道的连接
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return channelSftp;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    /**
     * 判断文件夹路径是否存在
     *
     * @param directory 文件夹路径，如：/root/test/saveFile/
     */
    public boolean isDirExist(String directory) {
        directory = null != directory && directory.endsWith("/") ? directory : directory + "/";
        boolean dirExist = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            dirExist = sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("no such file")) {
                dirExist = false;
            }
        }
        return dirExist;
    }

    /**
     * 创建一个文件夹(若整个路径都不存在会依次创建，若该路径已经存在则不会创建)
     *
     * @param createpath 要创建的文件夹路径，如：/root/test/saveFile/
     * @throws SftpException
     */
    public void createDir(String createpath) {
        createpath = null != createpath && createpath.endsWith("/") ? createpath : createpath + "/";
        if (!isDirExist(createpath)) {
            StringBuilder builder = new StringBuilder("/");
            String pathArry[] = createpath.split("/");
            for (String dir : pathArry) {
                if (!dir.equals("")) {
                    builder.append(dir);
                    builder.append("/");
                    try {
                        String path = builder.toString();
                        if (!isDirExist(path)) {
                            // 建立目录
                            channelSftp.mkdir(path);
                        }
                    } catch (SftpException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param deleteFile 要删除的文件路径，如:/root/test/saveFile/mylog.log
     */
    public void deleteFile(String deleteFile) {
        try {
            channelSftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     *
     * @param fileStram 文件输入流
     * @param upToPath  要上传到的文件夹路径
     * @param fileName  上传后的文件名
     */
    public void uploadFile(InputStream fileStram, String upToPath, String fileName) {
        upToPath = null != upToPath && upToPath.endsWith("/") ? upToPath : upToPath + "/";
        try {
            channelSftp.put(fileStram, upToPath + fileName);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param downlownPath 要下载的文件的所在文件夹路径
     * @param fileName     文件名
     * @return download  返回下载的文件流
     */
    public InputStream downloadFile(String downlownPath, String fileName) {
        downlownPath = null != downlownPath && downlownPath.endsWith("/") ? downlownPath : downlownPath + "/";
        InputStream download = null;
        try {
            download = channelSftp.get(downlownPath + fileName);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return download;
    }


    /**
     * 执行linux命令
     *
     * @param order 要执行的命令，（如，打印指定目录下的文件信息: ls -a /usr/local/kkFileView/kkFileView-4.0.0/bin/）
     * @return result  执行后返回的结果
     */
    public String excutOrder(String order) {
        String result = "";
        try {
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(order);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            InputStream in = channelExec.getInputStream();
            result = IOUtils.toString(in, Charset.defaultCharset());
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
