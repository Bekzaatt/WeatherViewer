package com.bekzataitymov.Repository.Impl;

import com.bekzataitymov.Entity.Locations;
import com.bekzataitymov.Repository.Interface.LocationRepository;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Repository
public class LocationRepositoryImpl implements LocationRepository {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    @Transactional
    public List<Locations> findAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Locations> locations = session.createQuery("From Locations", Locations.class).getResultList();
        return locations;
    }

    @Override
    @Transactional
    public Locations findById(Locations locations) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Locations WHERE id = :id";
        Locations tempLocations = session.createQuery(hql, Locations.class).setParameter("id", locations.getId()).uniqueResult();
        return tempLocations;
    }


    @Override
    @Transactional
    public Locations save(Locations location) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(location);
        return location;
    }

    @Override
    @Transactional
    public void delete(BigDecimal longitude, BigDecimal latitude, int userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "Delete From Locations WHERE Latitude = :latitude and Longitude = :longitude and userId = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public Locations findLocationByCoord(BigDecimal longitude, BigDecimal latitude, int userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "From Locations WHERE Latitude = :latitude and Longitude = :Longitude and userId = :userId";
        Query query = session.createQuery(hql, Locations.class);
        query.setParameter("latitude", latitude);
        query.setParameter("Longitude", longitude);
        query.setParameter("userId", userId);
        List<Locations> locations = query.getResultList();
        Locations location = null;
        if(locations.size() == 1){
            location = locations.get(0);
        }else if(locations.isEmpty()){
            throw new NoResultException();
        }
        return location;
    }
}
