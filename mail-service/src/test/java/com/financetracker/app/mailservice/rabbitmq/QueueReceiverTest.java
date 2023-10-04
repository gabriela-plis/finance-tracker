package com.financetracker.app.mailservice.rabbitmq;

import com.financetracker.api.mail.MailDTO;
import com.financetracker.app.mailservice.mail.MailCreator;
import com.financetracker.app.mailservice.mail.MailDeliverer;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

public class QueueReceiverTest {

    private final MailCreator mailCreator = mock();
    private final MailDeliverer mailDeliverer = mock();

    private final QueueReceiver queueReceiver = new QueueReceiver(mailCreator, mailDeliverer);

    @Test
    public void should_receive_message() {
//        given
        MailDTO mail = mock();
        MimeMessage mailToSend = mock();
        when(mailCreator.createMailToSend(mail)).thenReturn(mailToSend);

//        when
        queueReceiver.receive(mail);

//        then
        verify(mailCreator, times(1)).createMailToSend(mail);
        verify(mailDeliverer, times(1)).sendMail(mailToSend);
    }
}
