package org.sb.task.kafkamailservice.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private String fromMail = "${spring.mail.username}";

    @Value("${spring.mail.properties.mail.smtp.auth:true}")
    private boolean smtpAuth;

    public EmailServiceImpl(JavaMailSender sender){
        this.sender = sender;
    }

    public void sendEmail(String to, String subject, String body){
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromMail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body,true);

            System.out.println(fromEmail);
            System.out.println(to);
            System.out.println(subject);
            System.out.println(body);

            sender.send(message);
        }catch (Exception e){
            System.out.println(e);

        }
    }
}
