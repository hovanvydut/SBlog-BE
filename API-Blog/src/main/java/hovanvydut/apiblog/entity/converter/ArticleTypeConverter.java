package hovanvydut.apiblog.entity.converter;

import hovanvydut.apiblog.entity.enums.ArticleType;

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

        return attribute.type();
    }

    @Override
    public ArticleType convertToEntityAttribute(Integer dbData) {
        return ArticleType.of(dbData);
    }
}
