package hovanvydut.apiblog.core.listeners.registration;

import freemarker.template.TemplateException;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.model.entity.User;
import hovanvydut.apiblog.model.entity.VerificationToken;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final EmailService emailService;

    public RegistrationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        User user = onRegistrationCompleteEvent.getUser();
        VerificationToken token = onRegistrationCompleteEvent.getVerifyToken();

        sendRegisterEmail(user.getEmail(), user.getFullName(), token.getToken());
    }

    private void sendRegisterEmail(String email, String fullName, String token) {
        // TODO: create templateModel for each email type
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", fullName);
        templateModel.put("text", "Link kích hoạt tài khoản: <a href='http://localhost:3000/api/v1/auth/register/activation/"+ token +"/accept'>link</a>");
        templateModel.put("senderName", "Blog");

        try {
            this.emailService.sendMessageUsingFreemarkerTemplate(email, "Confirm your registration", templateModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
