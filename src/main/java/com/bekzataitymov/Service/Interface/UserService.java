package com.bekzataitymov.Service.Interface;

import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
public interface UserService {
    User find(String login, String password, HttpServletResponse response) throws UserPrincipalNotFoundException;

    void logout(String sessionsId, HttpServletResponse response);

    void setCookies(Sessions sessions, HttpServletResponse response);

    User findById(int id);

    User save(String login, String password, HttpServletResponse response);
}
