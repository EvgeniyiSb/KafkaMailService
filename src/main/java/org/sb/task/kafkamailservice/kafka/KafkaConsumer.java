package org.sb.task.kafkamailservice.kafka;

import org.sb.task.kafkamailservice.model.GlobalVar;
import org.sb.task.kafkamailservice.service.EmailService;
import org.sb.task.kafkamailservice.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class KafkaConsumer {

    @Autowired
    EmailService emailService;

    EmailServiceImpl emailServiceImpl;

    public KafkaConsumer(final EmailServiceImpl emailServiceImpl){
        this.emailServiceImpl = emailServiceImpl;
    }

    @Value("${email.recipients}")
    private String[] recipients;

    @KafkaListener(topics = GlobalVar.TOPIC_CREATE, groupId = "group_id")
    public void listenCreate(String message){
        for(String recipient : Arrays.asList(recipients)){
            emailServiceImpl.sendEmail(recipient, "Создание аккаунта", message);
        }
    }

    @KafkaListener(topics = GlobalVar.TOPIC_DELETE, groupId = "group_id")
    public void listenDelete(String message){
        for(String recipient : Arrays.asList(recipients)){
            emailServiceImpl.sendEmail(recipient, "Удаление акаунта", message);
        }
    }


}
