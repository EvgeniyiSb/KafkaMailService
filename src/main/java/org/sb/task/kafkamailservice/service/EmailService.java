package org.sb.task.kafkamailservice.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
