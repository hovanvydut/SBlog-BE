package com.debugbybrain.blog.core.listeners;

import com.debugbybrain.blog.common.enums.FreeMarkerTemplate;
import com.debugbybrain.blog.common.freemarker.ChangePasswordModel;
import com.debugbybrain.blog.core.listeners.event.ChangePasswordEvent;
import com.debugbybrain.blog.core.mail.EmailService;
import com.debugbybrain.blog.entity.User;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@Component
public class ChangePasswordListener implements ApplicationListener<ChangePasswordEvent> {

    private final EmailService emailService;

    public ChangePasswordListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(ChangePasswordEvent changePasswordEvent) {
        User user = changePasswordEvent.getUser();
        sendChanagePasswordEmail(user.getEmail(), user.getFullName(), LocalDateTime.now());
    }

    private void sendChanagePasswordEmail(String email, String fullName, LocalDateTime time) {
        ChangePasswordModel templateModel = new ChangePasswordModel()
                .setRecipientName(fullName)
                .setSenderName("SBlog 123");

        try {
            this.emailService.sendFreemarkerMail(email, ChangePasswordModel.mailTitle,
                    templateModel, FreeMarkerTemplate.CHANGE_PASSWORD);
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
