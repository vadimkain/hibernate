package com.kainv;

import com.kainv.entity.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.*;

class HibernateRunnerTest {
    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("kainv@gmail.com")
                .firstname("Vadim")
                .lastname("Kain")
                .birthDate(LocalDate.of(2000, 1, 19))
                .age(20)
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

//        Получаем аннотацию Table, которую ставили, чтобы описать название таблицы и схемы где находится наш юзер
        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(table -> table.schema() + '.' + table.name())
//                Если такой аннотации нет, то берём название класса
                .orElse(user.getClass().getName());

//        Получаем поля из класса
        Field[] declaredFields = user.getClass().getDeclaredFields();
        String columnNames = Arrays.stream(declaredFields)
//                У каждого поля получаем аннотацию @Column
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
//                        Если такая аннотация есть, то получаем название
                        .map(Column::name)
//                        В противном случае получаем название поля
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
//                Каждое поле преобразовываем в знак вопроса
                .map(field -> "?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        Теперь, в этом preparedStatement циклом нужно установить все поля
        for (Field declredField : declaredFields) {
//            Для того чтобы получить значение
            declredField.setAccessible(true);
//            По хорошему должен быть итератор, но тут опустим
            preparedStatement.setObject(1, declredField.get(user));
        }
    }
}