package com.debugbybrain.blog.common.converter;

import com.debugbybrain.blog.core.article.dto.PublishOption;
import org.springframework.core.convert.converter.Converter;

/**
 * @author hovanvydut
 * Created on 9/3/21
 */

public class PublishOptionConverter implements Converter<String, PublishOption> {

    @Override
    public PublishOption convert(String s) {
        try {
            return PublishOption.of(s);
        } catch (Exception ex) {
            return null;
        }
    }

}
