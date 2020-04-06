package com.asnovikova.bpmcamunda.delegate;

import com.asnovikova.bpmcamunda.component.EmailSender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Anna Novikova
 */
@Component("sendEmailDelegate")
public class SendEmailDelegate implements JavaDelegate {

    private final EmailSender emailSender;

    @Autowired
    public SendEmailDelegate(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String email = delegateExecution.getVariable("email").toString();
        String pictureFile = delegateExecution.getVariable("pictureFile").toString();
        emailSender.sendEmailWithAttachment(email, pictureFile);
    }
}
