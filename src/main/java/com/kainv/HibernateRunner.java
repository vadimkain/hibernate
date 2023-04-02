package com.kainv;

import com.kainv.entity.PersonalInfo;
import com.kainv.entity.User;
import com.kainv.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .username("vadim3@gmail.com")
                .personalInfo(
                        PersonalInfo.builder()
                                .lastname("Kain")
                                .firstname("Vadim")
                                .build()
                )
                .build();

//        Пишем лог уровня INFO
        log.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

//                Создаём лог уровня TRACE
                log.trace("Transaction is created, {}", transaction);

                session1.saveOrUpdate(user);
                log.trace("User is in persistent state: {} session {}", user, session1);

                session1.getTransaction().commit();
            }

//            Создаём лог уровня WARN. Это не ошибка, но стоит обратить внимание программисту
            log.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception exception) {
//            Создаём лог уровня ERROR и отображаем весь stacktrace
            log.error("Exception occurred", exception);
            throw exception;
        }
    }
}
