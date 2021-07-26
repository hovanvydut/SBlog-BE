package hovanvydut.apiblog.core.listeners.registration;

import org.springframework.context.ApplicationEvent;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

public class OnConfirmRegistrationEmailEvent extends ApplicationEvent {

    public OnConfirmRegistrationEmailEvent() {
        this(null);
    }

    private OnConfirmRegistrationEmailEvent(Object source) {
        super(new Object());
    }

}
