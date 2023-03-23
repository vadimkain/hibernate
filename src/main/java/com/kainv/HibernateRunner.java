package com.kainv;

import com.kainv.entity.User;
import com.kainv.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateRunner {
    public static void main(String[] args) {
        User user = User.builder()
                .username("kain@gmail.com")
                .lastname("Kain")
                .firstname("Vadim")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("Sveta");

////                У session2 вызываем метод get и получаем свежего пользователя
//                User freshUser = session2.get(User.class, user.getUsername());
////                Берём этого пользователя и устанавливаем туда все наши поля
//                freshUser.setLastname(user.getLastname());
//                freshUser.setFirstname(user.getFirstname());
////                И так далее

                Object mergedUser = session2.merge(user);

                session2.getTransaction().commit();
            }

        }
    }
}
