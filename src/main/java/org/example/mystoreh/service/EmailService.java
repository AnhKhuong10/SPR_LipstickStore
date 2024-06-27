package org.example.mystoreh.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMessage(String fromDisplayName, String subject, String text, String...to) throws MessagingException, UnsupportedEncodingException;
    void sendMessageWithAttachment(String fromDisplayName, String subject, String text, String pathToAttachment, String...to) throws MessagingException , UnsupportedEncodingException;
}
