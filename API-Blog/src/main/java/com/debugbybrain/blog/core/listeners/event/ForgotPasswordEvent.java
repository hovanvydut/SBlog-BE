package com.debugbybrain.blog.core.listeners.event;

import com.debugbybrain.blog.entity.PasswordResetToken;
import com.debugbybrain.blog.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@Getter
@Setter
public class ForgotPasswordEvent extends ApplicationEvent {

    private User user;
    private PasswordResetToken resetPasswordToken;

    public ForgotPasswordEvent(User user, PasswordResetToken passwordResetToken) {
        super(user);
        this.user = user;
        this.resetPasswordToken = passwordResetToken;
    }

}
