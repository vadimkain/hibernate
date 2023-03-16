package com.kainv.converter;

import com.kainv.entity.Birthday;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.util.Optional;

@Converter(autoApply = true)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {
    @Override
    public Date convertToDatabaseColumn(Birthday birthday) {
//        Проверяем, какое значение пришло (null/not null)
        return Optional.ofNullable(birthday)
//                Извлекаем дату
                .map(Birthday::birthDate)
//                Преобразовываем из java.sql.Date
                .map(Date::valueOf)
//                В противном случае возвращаем null
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date date) {
//        Проверяем, какое значение пришло (null/not null)
        return Optional.ofNullable(date)
//                Преобразовываем в LocalDate
                .map(Date::toLocalDate)
//                И создаём класс Birthday
                .map(Birthday::new)
//                Иначе возвращаем null, если не пришла date
                .orElse(null);
    }
}
