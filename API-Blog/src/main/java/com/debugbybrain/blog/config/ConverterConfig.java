package com.debugbybrain.blog.config;

import com.debugbybrain.blog.common.converter.PublishOptionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author hovanvydut
 * Created on 9/3/21
 */

@Configuration
public class ConverterConfig extends WebMvcConfigurationSupport {

    @Override
    public FormattingConversionService mvcConversionService() {
        FormattingConversionService f =  super.mvcConversionService();
        f.addConverter(new PublishOptionConverter());
        return f;
    }

}
