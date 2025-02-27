package com.bekzataitymov.Repository.Impl;

import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @Transactional
    public User save(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        Session session = sessionFactory.getCurrentSession();

        user.setPassword(hashedPassword);
        session.save(user);
        return user;
    }

    @Override
    @Transactional
    public User find(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        User user = null;
        String hql = "FROM User WHERE login = :login";

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("login", login);
        user = query.uniqueResult();

        if(user != null && !BCrypt.checkpw(password, user.getPassword())) {
            user = null;
        }

        return user;
    }

    @Override
    @Transactional
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User Where id = :id";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("id", id);
        User user = query.uniqueResult();
        return user;
    }
}
