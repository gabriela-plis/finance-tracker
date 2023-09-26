package com.financetracker.app.mailservice;

import com.financetracker.api.mail.MailDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailCreator mailCreator;

    public void sendMail(MailDTO mail) {
       MimeMessage message = mailCreator.createMessage(mail);
       javaMailSender.send(message);
    }
}
