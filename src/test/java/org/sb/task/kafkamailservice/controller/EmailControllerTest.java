package org.sb.task.kafkamailservice.controller;

import org.junit.jupiter.api.Test;
import org.sb.task.kafkamailservice.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    EmailServiceImpl emailService;

    @Value("${test.topic}")
    private String topic;

    @Test
    void test() throws Exception{
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        mockMvc.perform(post("/mail/api/send")
                        .param("to", "test@example.com")
                        .param("subject", "Test Subject")
                        .param("body", "Test Body"))
                .andExpect(status().isOk())
                .andExpect(content().string("Письмо успешно отправлено на test@example.com"));
    }
}
