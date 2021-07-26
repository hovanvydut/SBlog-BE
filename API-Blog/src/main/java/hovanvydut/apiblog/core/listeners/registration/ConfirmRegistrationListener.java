package hovanvydut.apiblog.core.listeners.registration;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Component
public class ConfirmRegistrationListener implements ApplicationListener<OnConfirmRegistrationEmailEvent> {

    @Override
    public void onApplicationEvent(OnConfirmRegistrationEmailEvent onConfirmRegistrationEmailEvent) {
        System.out.println("Sent email thank you!!");
    }

}
