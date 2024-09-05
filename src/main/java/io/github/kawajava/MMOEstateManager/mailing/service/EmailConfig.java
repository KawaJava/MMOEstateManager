package io.github.kawajava.MMOEstateManager.mailing.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailConfig {

    @Bean
    @ConditionalOnProperty(name="app.email.sender", havingValue = "emailStandardService")
    public EmailSender emailStandardService(JavaMailSender javaMailSender) {
        return new EmailStandardService(javaMailSender);
    }

    @Bean
    @ConditionalOnProperty(name="app.email.sender", matchIfMissing = true, havingValue = "fakeEmailService")
    public EmailSender fakeEmailService() {
        return new FakeEmailService();
    }
}
