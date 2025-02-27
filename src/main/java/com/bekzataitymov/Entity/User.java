package com.bekzataitymov.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2,max=255, message = "Username has to be between 2 and 255")
    @Column(name = "Login", nullable = false)
    private String login;
    @Size(min = 8,max=255, message = "Password has to be greater than 6")
    @NotEmpty(message = "Password should not be empty")
    @Column(name = "Password", nullable = false)
    private String password;

//    public User(String login, String password) {
//        this.login = login;
//        this.password = password;
//    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
}
