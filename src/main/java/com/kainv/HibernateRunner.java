package com.kainv;

import com.kainv.converter.BirthdayConverter;
import com.kainv.entity.Birthday;
import com.kainv.entity.Role;
import com.kainv.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
//        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure();

        try (
                SessionFactory sessionFactory = configuration.buildSessionFactory();
                Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();

            User user = User.builder()
                    .username("kainv@gmail.com")
                    .firstname("Vadim")
                    .lastname("Kain")
                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
                    .role(Role.ADMIN)
                    .build();

            session.save(user);

            session.getTransaction().commit();
        }
    }
}
