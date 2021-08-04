package hovanvydut.apiblog.common.freemarker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegistrationModel implements FreeMarkerTemplateModel {
    public static final String mailTitle = "Confirm your registration";

    private String recipientName;
    private String text;
    private String senderName;
}
