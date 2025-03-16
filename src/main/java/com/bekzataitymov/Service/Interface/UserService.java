package com.bekzataitymov.Service.Interface;

import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
public interface UserService {
    UserDTO find(String login, String password, HttpServletResponse response) throws UserPrincipalNotFoundException;

    void logout(String sessionsId, HttpServletResponse response);

    void setCookies(Sessions sessions, HttpServletResponse response);

    UserDTO findById(int id);

    UserDTO save(String login, String password, HttpServletResponse response);

    UserDTO findByCredentials(String username, String password, HttpServletResponse response);
}
