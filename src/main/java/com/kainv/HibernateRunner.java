package com.kainv;

import com.kainv.entity.Birthday;
import com.kainv.entity.Company;
import com.kainv.entity.PersonalInfo;
import com.kainv.entity.User;
import com.kainv.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        Company company = Company.builder()
                .name("Google")
                .build();

        User user = User.builder()
                .username("vadim@gmail.com")
                .personalInfo(
                        PersonalInfo.builder()
                                .lastname("Kain")
                                .firstname("Vadim")
                                .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                                .build()
                )
                .company(company)
                .build();

//        Пишем лог уровня INFO
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            try (session) {
                Transaction transaction = session.beginTransaction();

//                Создаём лог уровня TRACE
                log.trace("Transaction is created, {}", transaction);

                User user1 = session.get(User.class, 1L);

                session.getTransaction().commit();
            }
        }
    }
}
