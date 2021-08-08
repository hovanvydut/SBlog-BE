package hovanvydut.apiblog.entity;

import hovanvydut.apiblog.common.enums.ArticleVoteEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@Converter(autoApply = true)
public class ArticleVoteConverter implements AttributeConverter<ArticleVoteEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ArticleVoteEnum attribute) {
        if (attribute == null) {
            return 0;
        }

        return attribute.get();
    }

    @Override
    public ArticleVoteEnum convertToEntityAttribute(Integer dbData) {
        return ArticleVoteEnum.of(dbData);
    }
}
