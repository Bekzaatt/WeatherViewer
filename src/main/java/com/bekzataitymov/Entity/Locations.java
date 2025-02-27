package com.bekzataitymov.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name = "Locations")
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;
    @Column(name = "Name", nullable = false)
    private String name;
    @Column(name = "userId", nullable = false)
    private int userId;
    @Column(name = "Latitude", nullable = false)
    private BigDecimal Latitude;
    @Column(name = "Longitude", nullable = false)
    private BigDecimal Longitude;

    public Locations(String name, int userId, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.userId = userId;
        Latitude = latitude;
        Longitude = longitude;
    }
    public Locations(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getLatitude() {
        return Latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        Latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return Longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        Longitude = longitude;
    }
}
