package com.hai.tang.util;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * 用于连接远程服务器
 */
public class SftpUtils {
    private static JSch jsch;
    private static Session session = null;
    private static ChannelSftp channelSftp = null;

    //服务器用户名
    private String user;

    //服务器密码
    private String password;

    //服务器ip
    private String host;

    //服务器端口
    private int port;

    public SftpUtils() {
    }

    public SftpUtils(String host, int port, String user, String password) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 开启连接
     */
    public ChannelSftp connect() {
        jsch = new JSch();
        try {
            // 根据用户名、主机ip、端口号获取一个Session对象
            session = jsch.getSession(user, host, port);
            // 设置密码
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            // 为Session对象设置properties
            session.setConfig(config);
            // 设置连接超时为5秒
            session.setTimeout(100 * 50);
            // 通过Session建立连接
            session.connect();
            // 打开SFTP通道
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            // 建立SFTP通道的连接
            channelSftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return channelSftp;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (channelSftp != null) {
            try {
                channelSftp.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (session != null) {
            try {
                session.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            SftpATTRS sftpAttrs = channelSftp.lstat(directory);
            dirExist = sftpAttrs.isDir();
        } catch (Exception e) {
            if ("no such file".equalsIgnoreCase(e.getMessage())) {
                return false;
            }
        }
        return dirExist;
    }

    /**
     * 创建一个文件夹(若整个路径都不存在会依次创建，若该路径已经存在则不会创建)
     *
     * @param createpath 要创建的文件夹路径，如：/root/test/saveFile/
     */
    public void createDir(String createpath) {
        createpath = null != createpath && createpath.endsWith("/") ? createpath : createpath + "/";
        if (!isDirExist(createpath)) {
            StringBuilder builder = new StringBuilder("/");
            String[] pathArry = createpath.split("/");
            for (String dir : pathArry) {
                if (!"".equals(dir)) {
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
        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
