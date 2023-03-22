package com.kainv;

import com.kainv.converter.BirthdayConverter;
import com.kainv.entity.Birthday;
import com.kainv.entity.Role;
import com.kainv.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
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
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (
                SessionFactory sessionFactory = configuration.buildSessionFactory();
                Session session = sessionFactory.openSession()
        ) {
            session.beginTransaction();

//            User user = User.builder()
//                    .username("kainv9@gmail.com")
//                    .firstname("Vadim")
//                    .lastname("Kain")
//                    .info("""
//                            {
//                                "name" : "Vadim",
//                                "id" : 25
//                            }
//                            """)
//                    .birthDate(new Birthday(LocalDate.of(2000, 1, 19)))
//                    .role(Role.ADMIN)
//                    .build();
//            session.delete(user);

            User user1 = session.get(User.class, "kainv@gmail.com");
//            User user2 = session.get(User.class, "kainv@gmail.com");

            user1.setLastname("Petrov2");
            session.flush();

            System.out.println(session.isDirty());


//            session.evict(user1);
//            session.clear();
//            session.close();

            session.getTransaction().commit();
        }
    }
}
