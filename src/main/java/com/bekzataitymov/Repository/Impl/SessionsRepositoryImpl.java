package com.bekzataitymov.Repository.Impl;

import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class SessionsRepositoryImpl implements SessionsRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Transactional
    @Override
    public Sessions save(User user){
        Session session = sessionFactory.getCurrentSession();

        LocalDateTime now = LocalDateTime.now();
        Sessions sessions = new Sessions();

        sessions.setUserId(user.getId());
        sessions.setExpiresAt(now.plusSeconds(20*64*64));
        session.save(sessions);
        return sessions;
    }
    @Transactional
    @Override
    public Sessions findByUser(User user){
        Session session = sessionFactory.getCurrentSession();

        Query<Sessions> query = session.createQuery("From Sessions where userId = :userId")
                .setParameter("userId", user.getId());
        Sessions sessions = query.uniqueResult();

        return sessions;
    }
    @Override
    @Transactional
    public Sessions update(Sessions sessions){
        Session session = sessionFactory.getCurrentSession();
        LocalDateTime now = LocalDateTime.now();

        sessions.setExpiresAt(now.plusSeconds(20));
        session.update(sessions);
        session.flush();
        return sessions;
    }

    @Transactional
    @Override
    public Sessions findById(String id){
        Session session = sessionFactory.getCurrentSession();

        return session.get(Sessions.class, id);
    }

    @Override
    @Transactional
    public void delete(String sessionsId) {
        Session session = sessionFactory.getCurrentSession();
        Sessions sessions = findById(sessionsId);
        if(sessions != null) {
            session.delete(sessions);
        }
    }

}
