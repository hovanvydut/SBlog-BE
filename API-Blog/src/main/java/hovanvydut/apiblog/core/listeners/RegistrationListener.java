package hovanvydut.apiblog.core.listeners;

import freemarker.template.TemplateException;
import hovanvydut.apiblog.common.enums.FreeMarkerTemplate;
import hovanvydut.apiblog.common.freemarker.RegistrationModel;
import hovanvydut.apiblog.core.listeners.event.RegistrationCompleteEvent;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.VerificationToken;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final EmailService emailService;

    public RegistrationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent registrationCompleteEvent) {
        User user = registrationCompleteEvent.getUser();
        VerificationToken token = registrationCompleteEvent.getVerifyToken();

        sendRegisterEmail(user.getEmail(), user.getFullName(), token.getToken());
    }

    private void sendRegisterEmail(String email, String fullName, String token) {
        RegistrationModel templateModel = new RegistrationModel().setRecipientName(fullName)
                .setText("Link kích hoạt tài khoản: <a href='http://localhost:3000/api/v1/auth/register/activation/"+ token +"/accept'>link</a>")
                .setSenderName("Blog 12345");

        try {
            this.emailService.sendFreemarkerMail(email, RegistrationModel.mailTitle, templateModel, FreeMarkerTemplate.REGISTRATION);
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
