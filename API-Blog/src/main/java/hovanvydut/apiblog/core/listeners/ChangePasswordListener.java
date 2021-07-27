package hovanvydut.apiblog.core.listeners;

import freemarker.template.TemplateException;
import hovanvydut.apiblog.common.enums.FreeMarkerTemplate;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.model.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
        // TODO: create templateModel for each email type
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", fullName);
        templateModel.put("senderName", "SBlog");

        try {
            this.emailService.sendFreemarkerMail(email, "Your password has been change" , templateModel, FreeMarkerTemplate.CHANGE_PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
