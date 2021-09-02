package com.debugbybrain.blog.core.listeners;

import com.debugbybrain.blog.core.listeners.event.ConfirmRegistrationEmailEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Component
public class ConfirmRegistrationListener implements ApplicationListener<ConfirmRegistrationEmailEvent> {

    @Override
    public void onApplicationEvent(ConfirmRegistrationEmailEvent confirmRegistrationEmailEvent) {
        System.out.println("Sent email thank you!!");
    }

}
