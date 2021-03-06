package com.debugbybrain.blog.entity.converter;

import com.debugbybrain.blog.entity.enums.ArticleVoteType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@Converter(autoApply = true)
public class ArticleVoteConverter implements AttributeConverter<ArticleVoteType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ArticleVoteType attribute) {
        if (attribute == null) {
            return 0;
        }

        return attribute.get();
    }

    @Override
    public ArticleVoteType convertToEntityAttribute(Integer dbData) {
        return ArticleVoteType.of(dbData);
    }
}
