package hovanvydut.apiblog.entity.converter;

import hovanvydut.apiblog.common.enums.ArticleType;

import javax.persistence.AttributeConverter;

/**
 * @author hovanvydut
 * Created on 8/8/21
 */

public class ArticleTypeConverter implements AttributeConverter<ArticleType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ArticleType attribute) {
        if (attribute == null) {
            return 0;
        }

        return attribute.get();
    }

    @Override
    public ArticleType convertToEntityAttribute(Integer dbData) {
        return null;
    }
}
