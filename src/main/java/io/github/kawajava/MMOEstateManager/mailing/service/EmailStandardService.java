package io.github.kawajava.MMOEstateManager.mailing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailStandardService implements EmailSender {

    private final JavaMailSender mailSender;

    @Value("$email.for.sending.gold.asking")
    private String email;

    @Override
    @Async
    public void send(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("MMOEstateManager <"+ email + ">");
        mailMessage.setReplyTo("MMOEstateManager <" + email + ">");
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

}
