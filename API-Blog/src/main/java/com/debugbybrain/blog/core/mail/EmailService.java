package com.debugbybrain.blog.core.mail;

import com.debugbybrain.blog.common.enums.FreeMarkerTemplate;
import com.debugbybrain.blog.common.freemarker.FreeMarkerTemplateModel;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

/**
 * @author hovanvydut
 * Created on 7/6/21
 */

public interface EmailService {
    public void sendSimpleMessage(String to,  String subject, String text);
    public void sendSimpleMessageUsingTemplate(String to, String subject, String ...templateModel);
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);
    public void sendThymeleafMail(String to, String subject, Map<String, Object> templateModel)
            throws IOException, MessagingException;
    public void sendFreemarkerMail(String to, String subject, FreeMarkerTemplateModel templateModel, FreeMarkerTemplate template)
            throws IOException, TemplateException, MessagingException;
}
