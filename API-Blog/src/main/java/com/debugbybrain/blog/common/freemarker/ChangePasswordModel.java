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
public class ChangePasswordModel implements FreeMarkerTemplateModel {
    public static final String mailTitle = "Your password has been change 1";

    private String recipientName;
    private String senderName;
}
