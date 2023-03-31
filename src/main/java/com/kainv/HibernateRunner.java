package com.kainv;

import com.kainv.entity.User;
import com.kainv.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HibernateRunner {
    public static final Logger logger = LoggerFactory.getLogger(HibernateRunner.class);

    public static void main(String[] args) {
        User user = User.builder()
                .username("kain@gmail.com")
                .lastname("Kain")
                .firstname("Vadim")
                .build();

//        Пишем лог уровня INFO
        logger.info("User entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

//                Создаём лог уровня TRACE
                logger.trace("Transaction is created, {}", transaction);

                session1.saveOrUpdate(user);
                logger.trace("User is in persistent state: {} session {}", user, session1);

                session1.getTransaction().commit();
            }

//            Создаём лог уровня WARN. Это не ошибка, но стоит обратить внимание программисту
            logger.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception exception) {
//            Создаём лог уровня ERROR и отображаем весь stacktrace
            logger.error("Exception occurred", exception);
            throw exception;
        }
    }
}
