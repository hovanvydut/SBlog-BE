package com.debugbybrain.blog.common.enums;

/**
 * @author hovanvydut
 * Created on 7/27/21
 */

public enum FreeMarkerTemplate {
    REGISTRATION("registration-email-template.ftl"),
    CHANGE_PASSWORD("change-password-email-template.ftl"),
    FORGOT_PASSWORD("forgot-password-email-template.ftl");

    private String templateFileName;

    FreeMarkerTemplate (String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String get() {
        return this.templateFileName;
    }
}
