package com.bekzataitymov.Repository.Impl;

import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.UserRepositoryTest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImplTest implements UserRepositoryTest {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public User save(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return (User) session.save(user);
    }
}
