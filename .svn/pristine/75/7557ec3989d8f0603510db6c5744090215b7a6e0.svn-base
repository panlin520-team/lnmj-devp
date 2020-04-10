package com.lnmj.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: panlin
 * @create: 2019-05-05 10:43
 **/
@Component
public class MailUtil{

    @Value("${spring.mail.username}")
    private String whoAmI;

    @Resource
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件
     * @param to 发送给谁
     * @param subject 邮件主题
     * @param content 邮件内容
     */

    public  void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println("xixi:"+whoAmI);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(whoAmI);
        mailSender.send(message);
    }

    /**
     * 发送html邮件
     * @param to
     * @param subject
     * @param content
     */

    public  void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setFrom(whoAmI);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送附件邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setFrom(whoAmI);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            //此处可以添加多个附件 zjy0910
            helper.addAttachment(fileName,file);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送图片邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath 图片路径
     * @param rscId
     */

    public void sendInlinResourceMail(String to, String subject, String content,
                                      String rscPath, String rscId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(whoAmI);
            helper.setSubject(subject);
            helper.setText(content,true);

            //可以添加多个图片
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId,res);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public  boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
}
