package hovanvydut.apiblog.core.listeners.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author hovanvydut
 * Created on 7/26/21
 */

@Getter
@Setter
public class ConfirmRegistrationEmailEvent extends ApplicationEvent {

    public ConfirmRegistrationEmailEvent() {
        this(null);
    }

    private ConfirmRegistrationEmailEvent(Object source) {
        super(new Object());
    }

}
