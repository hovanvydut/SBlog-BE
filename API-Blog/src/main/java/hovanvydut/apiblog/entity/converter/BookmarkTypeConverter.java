package hovanvydut.apiblog.entity.converter;

import hovanvydut.apiblog.entity.enums.BookmarkType;

import javax.persistence.AttributeConverter;

/**
 * @author hovanvydut
 * Created on 8/15/21
 */

public class BookmarkTypeConverter implements AttributeConverter<BookmarkType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookmarkType attribute) {
        if (attribute == null) {
            return 0;
        }

        return attribute.type();
    }

    @Override
    public BookmarkType convertToEntityAttribute(Integer dbData) {
        return BookmarkType.of(dbData);
    }
}
