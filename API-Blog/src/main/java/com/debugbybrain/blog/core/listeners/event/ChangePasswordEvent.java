package com.debugbybrain.blog.core.listeners.event;

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
public class ChangePasswordEvent extends ApplicationEvent {

    private User user;

    public ChangePasswordEvent(User user) {
        super(user);
        this.user = user;
    }

}
