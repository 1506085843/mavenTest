package com.hai.tang.model;

/**邮件发送配置*/
public class EmailConfig {
    //邮箱的类型：0-SMTP 1-IMAP 2-Exchange
    private Integer mailPro;
    //是否开启身份验证 0-开启 1-关闭
    private Integer auth;
    //TLS加密传输
    private Integer tls;
    //SSL加密传输
    private Integer ssl;
    //发信名称
    private String alias;
    //发件人邮箱
    private String servicedMailAddress;
    //发件人密码或授权码
    private String password;
    //发件服务器地址
    private String sendMailHost;
    //发件服务器端口
    private String sendMailPort;

    public Integer getMailPro() {
        return mailPro;
    }

    public void setMailPro(Integer mailPro) {
        this.mailPro = mailPro;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public Integer getTls() {
        return tls;
    }

    public void setTls(Integer tls) {
        this.tls = tls;
    }

    public Integer getSsl() {
        return ssl;
    }

    public void setSsl(Integer ssl) {
        this.ssl = ssl;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getServicedMailAddress() {
        return servicedMailAddress;
    }

    public void setServicedMailAddress(String servicedMailAddress) {
        this.servicedMailAddress = servicedMailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSendMailHost() {
        return sendMailHost;
    }

    public void setSendMailHost(String sendMailHost) {
        this.sendMailHost = sendMailHost;
    }

    public String getSendMailPort() {
        return sendMailPort;
    }

    public void setSendMailPort(String sendMailPort) {
        this.sendMailPort = sendMailPort;
    }
}
