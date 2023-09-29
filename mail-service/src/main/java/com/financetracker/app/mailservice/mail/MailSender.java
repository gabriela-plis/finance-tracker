package com.financetracker.app.mailservice.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSender {

    private final JavaMailSender javaMailSender;

    public void sendMail(MimeMessage mail) {
       javaMailSender.send(mail);
    }
}
