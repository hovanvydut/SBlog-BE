package hovanvydut.apiblog.core.listeners;

import freemarker.template.TemplateException;
import hovanvydut.apiblog.common.enums.FreeMarkerTemplate;
import hovanvydut.apiblog.common.freemarker.ForgotPasswordModel;
import hovanvydut.apiblog.core.listeners.event.ForgotPasswordEvent;
import hovanvydut.apiblog.core.mail.EmailService;
import hovanvydut.apiblog.model.entity.PasswordResetToken;
import hovanvydut.apiblog.model.entity.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

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

        sendChangeForgotPasswordEmail(user.getEmail(), user.getFullName(), resetToken.getToken());
    }

    private void sendChangeForgotPasswordEmail(String email, String fullName, String token) {
        ForgotPasswordModel templateModel = new ForgotPasswordModel()
                .setRecipientName(fullName)
                .setToken(token)
                .setSenderName("SBlog 1234");

        try {
            this.emailService.sendFreemarkerMail(email, ForgotPasswordModel.mailTitle , templateModel, FreeMarkerTemplate.FORGOT_PASSWORD);
        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }
    }

}
