package com.example.demo.service;

import com.example.demo.exeptions.RedditException;
import com.example.demo.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {


    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendEmail(NotificationEmail notificationEmail){
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("redditclone@email.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessage.setSubject(notificationEmail.getSubject());
            mimeMessage.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("activation Mail sent");
        }catch (MailException e){
            log.error("Exeption occurred when sending mail", e);
            throw new RedditException("Exeption occurred when sending email to" +notificationEmail.getRecipient());
        }
    }

}
