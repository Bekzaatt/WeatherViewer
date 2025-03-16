package com.bekzataitymov.Entity.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    @Id
    private int id;
    @NotBlank(message = "Name shouldn't be empty")
    @Size(min = 2, message = "Name should be at least 2 letters")
    private String username;
    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 6, message = "Name should be at least 6 letters")
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setId(int id) {
        this.id = id;
    }
}
