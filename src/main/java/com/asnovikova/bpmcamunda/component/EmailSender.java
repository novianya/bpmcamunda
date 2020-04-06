package com.asnovikova.bpmcamunda.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Anna Novikova
 */
@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    Logger logger  = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithAttachment(String email, String pictureFile) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);
        helper.setSubject("catfacts");
        helper.setText("<h1>Check attachment for image!</h1>", true);

        // hard coded a file path
        FileSystemResource file = new FileSystemResource(new File(pictureFile));

        helper.addAttachment("pic.png", file);
        logger.info("will send email {} {}", email, pictureFile);

        javaMailSender.send(msg);
    }
}
