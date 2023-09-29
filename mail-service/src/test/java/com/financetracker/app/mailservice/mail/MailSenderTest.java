package com.financetracker.app.mailservice.mail;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class MailSenderTest {

    private final JavaMailSender javaMailSender = mock();

    private final MailSender mailSender = new MailSender(javaMailSender);

    @Test
    public void should_send_mail() {
//        given
        MimeMessage mailToSend = mock();

//        when
        mailSender.sendMail(mailToSend);

//        then
        verify(javaMailSender, times(1)).send(mailToSend);
    }
}
