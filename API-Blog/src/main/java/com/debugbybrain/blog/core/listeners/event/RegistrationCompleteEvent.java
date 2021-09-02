package com.debugbybrain.blog.core.listeners.event;

import com.debugbybrain.blog.entity.User;
import com.debugbybrain.blog.entity.VerificationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final VerificationToken verifyToken;

    public RegistrationCompleteEvent(User user, VerificationToken verifyToken) {
        super(user);
        this.user = user;
        this.verifyToken = verifyToken;
    }

}
