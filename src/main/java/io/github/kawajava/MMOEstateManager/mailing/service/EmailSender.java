package io.github.kawajava.MMOEstateManager.mailing.service;

public interface EmailSender {
    void send(String to, String subject, String message);
}
