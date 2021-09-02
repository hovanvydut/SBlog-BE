package com.debugbybrain.blog.common.freemarker;

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
public class ForgotPasswordModel implements FreeMarkerTemplateModel {
    public static final String mailTitle = "Forgot Password 1";

    private String recipientName;
    private String token;
    private String senderName;
}
