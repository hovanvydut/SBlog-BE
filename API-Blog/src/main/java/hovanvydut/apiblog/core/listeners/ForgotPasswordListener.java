package hovanvydut.apiblog.core.listeners;

import freemarker.template.TemplateException;
import hovanvydut.apiblog.common.enums.FreeMarkerTemplate;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.model.entity.PasswordResetToken;
import hovanvydut.apiblog.model.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@Component
public class ForgotPasswordListener implements ApplicationListener<ForgotPasswordEvent> {

    private final EmailService emailService;

    public ForgotPasswordListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(ForgotPasswordEvent forgotPasswordEvent) {
        User user = forgotPasswordEvent.getUser();
        PasswordResetToken resetToken = forgotPasswordEvent.getResetPasswordToken();

        sendChanageForgotPasswordEmail(user.getEmail(), user.getFullName(), resetToken.getToken());
    }

    private void sendChanageForgotPasswordEmail(String email, String fullName, String token) {
        // TODO: create templateModel for each email type
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", fullName);
        templateModel.put("token", token);
        templateModel.put("senderName", "SBlog");

        // TODO: define constant variable title email, ... to another class
        try {
            this.emailService.sendFreemarkerMail(email, "Forgot Password" , templateModel, FreeMarkerTemplate.FORGOT_PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
