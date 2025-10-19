package org.sb.task.kafkamailservice.controller;

import org.sb.task.kafkamailservice.kafka.KafkaProducer;
import org.sb.task.kafkamailservice.model.GlobalVar;
import org.sb.task.kafkamailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class EmailController {

    @Autowired
    EmailService emailService;

    KafkaProducer kafkaProducer;

    public EmailController(KafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/kafka/user/created")
    public ResponseEntity<String> userDeletedMessage(@RequestParam("message") String message){
        try {
            kafkaProducer.sendMessage(GlobalVar.TOPIC_CREATE, message);

            return ResponseEntity.ok().body("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка отправки сообщения: " + e);
        }
    }

    @PostMapping("/kafka/user/deleted")
    public ResponseEntity<String> userCreatedMessage(@RequestParam("message") String message){
        try {
            kafkaProducer.sendMessage(GlobalVar.TOPIC_DELETE, message);

            return ResponseEntity.badRequest().body("Здравствуйте! Ваш аккаунт был удалён.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка отправки сообщения: " + e);
        }
    }

    @PostMapping("/api/send")
    public ResponseEntity<String> sendEmail(@RequestParam String to,
                                            @RequestParam String subject,
                                            @RequestParam String body){

        try {
            emailService.sendEmail(to, subject, body);

            return ResponseEntity.ok("Письмо отправлено.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка отправки сообщения: " + e);
        }
    }
}
