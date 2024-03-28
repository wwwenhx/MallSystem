package com.drug.graduate.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sendMailCode(String to, String title, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); //发送人
        message.setTo(to);   //收件人
        message.setSubject(title);  //邮件名
        message.setText(content);   //邮件内容（验证码）
        mailSender.send(message);

    }

}
