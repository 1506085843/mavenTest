package com.hai.tang.util.email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.hai.tang.model.EmailConfig;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;
import com.sun.mail.util.MailSSLSocketFactory;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;

public class EmailUtils {

    // 邮件发送的路径
    public final static String MAIL_URL = "";
    // 附件支持的类型
    public final static String MAIL_TYPE = "xls,xlsx,pdf,zip,txt,rar,ppt,pptx,doc,docx";
    // 最大长度10M
    public static final int MAX_BYTE_NUM = 10 * 1024 * 1024;


    /**
     * mailConfigRsp 发件服务器及发件人配置
     * recipientEmails 收件人邮箱
     * cc 抄送邮箱
     * subject  邮件主题
     * content 邮件内容
     * attachPath 要发送的附件路径
     **/
    public static void sendMail(EmailConfig mailConfigRsp, String[] recipientEmails, String[] cc, String subject, String content, String[] attachPath) {
        if (null != content) {
            content = content.replaceAll("\n", "<br/>");
        }
        //Exchange
        if ("2".equals(mailConfigRsp.getMailPro().toString())) {
            try {
                List<String> attachments = attachPath == null ? new ArrayList<>() : Arrays.asList(attachPath);
                ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
                ExchangeClient client = new ExchangeClient.ExchangeClientBuilder().hostname(mailConfigRsp.getSendMailHost())
                        .exchangeVersion(ExchangeVersion.Exchange2010_SP2).username(mailConfigRsp.getServicedMailAddress())
                        .password(mailConfigRsp.getPassword()).recipientTos(Arrays.asList(recipientEmails))
                        .subject(subject).message(content)
                        .attachments(attachments)
                        .build();
                //发送邮件
                client.sendExchange(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Message mailMessage = createMessage(createSession(mailConfigRsp), mailConfigRsp.getServicedMailAddress(), mailConfigRsp.getAlias(), recipientEmails, cc, subject, content, attachPath);
                if (mailMessage.getSize() > MAX_BYTE_NUM) {
                    int max = MAX_BYTE_NUM / 1024 / 1024;
                    System.out.println("内容+附件最大只能 " + max + " M.....");
                    return;
                }
                // 发送邮件
                Transport.send(mailMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static Message createMessage(Session session, String account, String alias, String[] notifyTo, String[] cc, String subject,
                                        String content, String[] attachFileNames) {
        if (attachFileNames == null || attachFileNames.length < 1) {
            return createTextMessage(session, account, alias, notifyTo, cc, subject, content);
        } else {
            return createHtmlMessage(session, account, alias, notifyTo, cc, subject, content, attachFileNames);
        }
    }

    public static Session createSession(EmailConfig mailConfigRsp) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailConfigRsp.getSendMailHost());
        properties.put("mail.smtp.port", mailConfigRsp.getSendMailPort());

        if ("0".equals(mailConfigRsp.getAuth().toString())) {
            properties.put("mail.user", mailConfigRsp.getServicedMailAddress());
            properties.put("mail.password", mailConfigRsp.getPassword());
            properties.put("mail.smtp.auth", "true");// 普通连接的属性
        } else {
            properties.put("mail.smtp.auth", "false");// 普通连接的属性
        }

        if ("1".equals(mailConfigRsp.getSsl().toString())) {
            MailSSLSocketFactory sslSF = null;
            try {
                sslSF = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            if (sslSF != null) {
                sslSF.setTrustAllHosts(true);
            }
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sslSF);
            properties.put("mail.transport.protocol", "smtps");
            if ("0".equals(mailConfigRsp.getAuth().toString())) {
                properties.put("mail.smtps.auth", "true");
            } else {
                properties.put("mail.smtps.auth", "false");
            }
        } else {
            properties.put("mail.transport.protocol", "smtp");
        }
        if ("1".equals(mailConfigRsp.getTls().toString())) {
            properties.put("mail.smtp.starttls.enable", "true");
        } else {
            properties.put("mail.smtp.starttls.enable", "false");
        }
        Session session;
        if ("0".equals(mailConfigRsp.getAuth().toString())) {
            session = Session.getInstance(properties,
                    new mailAuthenricator(mailConfigRsp.getServicedMailAddress(), mailConfigRsp.getPassword()));
        } else {
            session = Session.getInstance(properties);
        }
        session.setDebug(true);
        return session;
    }

    public static Message createHtmlMessage(Session session, String account, String alias, String[] notifyTo, String[] cc, String subject,
                                            String content, String[] attachFileNames) {
        Message mimeMessage = createNotConnetMessage(session, account, alias, notifyTo, cc, subject);
        try {
            // 容器类，可以包含多个MimeBodyPart对象
            Multipart mp = new MimeMultipart("mixed");

            // MimeBodyPart可以包装文本，图片，附件
            MimeBodyPart body = new MimeBodyPart();
            // HTML正文
            body.setContent(content, "text/html; charset=UTF-8");
            mp.addBodyPart(body);
            // 添加图片&附件
            attachBodyPart(attachFileNames, mp);
            // 设置邮件内容
            mimeMessage.setContent(mp);
            // 仅仅发送文本
            // mimeMessage.setText(content);
            mimeMessage.saveChanges();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;
    }

    public static Message createNotConnetMessage(Session session, String account, String alias, String[] notifyTo, String[] cc, String subject) {
        Message mimeMessage = new MimeMessage(session);
        // 发件人
        try {
            if (null != alias && alias.length() > 0) {
                alias = alias.trim();
            }
            mimeMessage.setFrom(new InternetAddress(account, alias));
            // 收件人
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress(notifyTo));
            // 抄送人
            List<String> ccStr = new ArrayList<>();
            if (null != cc) {
                for (String copy : cc) {
                    if (null != copy && copy.length() > 0) {
                        ccStr.add(copy);
                    }
                }
                String[] copyTo = ccStr.toArray(new String[0]);
                mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress(copyTo));
            }
            // 主题
            mimeMessage.setSubject(subject);
            // 时间
            mimeMessage.setSentDate(new Date());
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mimeMessage;
    }

    public static void attachBodyPart(String[] attachFileNames, Multipart mp) {
        int len = 0;
        if (attachFileNames == null || (len = attachFileNames.length) < 1) {
            return;
        }
        String attachFileName;
        String path;
        int byteNum = 0;
        MimeBodyPart bodyPart;
        for (int i = 0; i < len; i++) {
            attachFileName = attachFileNames[i];
            if (null == attachFileName || attachFileName.length() == 0) {
                continue;
            }
            // 判断附件是否是支持的文件类型
            String[] fileNameArr = attachFileName.split("\\.");
            if (!MAIL_TYPE.contains(fileNameArr[fileNameArr.length - 1])) {
                System.out.println("该文件不支持发送 ，文件名 ：   " + attachFileName);
                continue;
            }
            // 设置附件的完整路径
            path = MAIL_URL + attachFileName;
            // 该文件的大小
            File file = new File(path);
            if (!file.exists() || !file.isFile()) {
                System.out.println("该文件不存在,附件中不发送：   attachFileName =" + attachFileName);
                continue;
            }
            byteNum += file.length();// 每个文件大小开始计算
            FileDataSource fds = new FileDataSource(path);// 得到数据源
            bodyPart = new MimeBodyPart();
            try {
                bodyPart.setContent("", "text/html;charset=UTF-8");
                bodyPart.setDataHandler(new DataHandler(fds));
                bodyPart.setFileName(MimeUtility.encodeText(fds.getName())); // 得到文件名并编码（防止中文文件名乱码）同样放入BodyPart
                mp.addBodyPart(bodyPart);
            } catch (MessagingException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    static class mailAuthenricator extends Authenticator {
        String userName = null;
        String passWord = null;

        public mailAuthenricator(String userName, String passWord) {
            super();
            this.userName = userName;
            this.passWord = passWord;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, passWord);
        }
    }

    public static Message createTextMessage(Session session, String account, String alias, String[] notifyTo, String[] cc, String subject,
                                            String content) {
        Message mimeMessage = createNotConnetMessage(session, account, alias, notifyTo, cc, subject);
        try {
            Multipart mp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html; charset=UTF-8");
            mp.addBodyPart(bodyPart);
            mimeMessage.setContent(mp);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mimeMessage;
    }

    public static Address[] InternetAddress(String[] notifyTos) {
        int length = 0;
        if (notifyTos == null || (length = notifyTos.length) < 1) {
            return null;
        }
        Address[] address = new Address[length];
        for (int i = 0; i < length; i++) {
            try {
                address[i] = new InternetAddress(notifyTos[i]);
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }
        return address;
    }
}
