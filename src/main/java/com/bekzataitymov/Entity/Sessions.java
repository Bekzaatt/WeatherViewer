package com.bekzataitymov.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Session")
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id")
    private String id;
    @Column(name = "UserID")
    private int userId;
    @Column(name = "ExpiresAt")
    private LocalDateTime expiresAt;

    public Sessions(String id, int userId, LocalDateTime expiresAt) {
        this.id = id;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    public Sessions(){

    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getId() {
        return id;
    }
}
