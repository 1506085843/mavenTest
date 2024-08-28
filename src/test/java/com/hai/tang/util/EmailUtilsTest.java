package com.hai.tang.util;

import com.hai.tang.model.EmailConfig;
import com.hai.tang.util.email.EmailUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("邮件发送测试类")
public class EmailUtilsTest {
    @Test
    @DisplayName("发送邮件")
    public void sendEmail() {
        //发送服务器和发送方配置
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setMailPro(0);
        emailConfig.setAuth(0);
        emailConfig.setTls(0);
        emailConfig.setSsl(0);
        emailConfig.setAlias("张三");
        emailConfig.setSendMailHost("smtp.qq.com");
        emailConfig.setSendMailPort("25");
        emailConfig.setServicedMailAddress("111@qq.com");
        emailConfig.setPassword("ervnhhvbhghj");

        //收件方（多个收件方分号分割）
        String recipientEmailsStr = "2537116234@qq.com";
        String[] recipientEmails = recipientEmailsStr.split(";");

        //要抄送的邮箱（多个抄送方分号分割）
        String ccEmailsStr = "333@163.com;444@163.com";
        String[] ccEmails = ccEmailsStr.split(";");

        //邮件主题
        String subject = "邮件主题";
        //邮件内容
        String content = "第一行件内容\n第二行件内容\n第三行件内容\n";
        //要发送的附件文件路径
        String[] attachPath = new String[] {"C:\\Users\\haitang\\Downloads\\1202.xls", "C:\\Users\\haitang\\Downloads\\逗号拼接临时文件.txt"};

        //发送邮件
        EmailUtils.sendMail(emailConfig, recipientEmails, ccEmails,subject, content, attachPath);
        System.out.println("邮件发送成功，内容如下： 发件人" + emailConfig.getServicedMailAddress() + "，接收者" + Arrays.toString(recipientEmails) + ",主题" + subject + ",内容" + content + ",附件" + Arrays.toString(attachPath));
    }
}
