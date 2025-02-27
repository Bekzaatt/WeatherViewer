package com.bekzataitymov.Service.Impl;

import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import com.bekzataitymov.Repository.Interface.UserRepository;
import com.bekzataitymov.Service.Interface.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionsRepository sessionsRepository;

    @Override
    public User save(String login, String password, HttpServletResponse response) {

        User existingUser = userRepository.find(login, password);
        if(existingUser != null){
            throw new RuntimeException("Попробуйте ввести другой логин или пароль");
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userRepository.save(user);
        Sessions sessions = sessionsRepository.save(user);
        setCookies(sessions, response);

        return user;
    }

    @Override
    public User find(String login, String password, HttpServletResponse response) throws UserPrincipalNotFoundException {
        User user = userRepository.find(login, password);
        if (user == null){
            throw new UserPrincipalNotFoundException("User not found");
        }
        Sessions sessions = sessionsRepository.findByUser(user);

        if(sessions != null && sessions.getExpiresAt().isBefore(LocalDateTime.now())){
            sessionsRepository.delete(sessions.getId());
            sessions = null;
        }
        if(sessions == null){
            sessions = sessionsRepository.save(user);
            setCookies(sessions, response);
        }
        return user;
    }


    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void logout(String sessionsId, HttpServletResponse response) {
        System.out.println("logout method");
        if(sessionsId != null) {
            sessionsRepository.delete(sessionsId);
            Cookie cookie = new Cookie("session", sessionsId);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }


    }

    public void setCookies(Sessions sessions, HttpServletResponse response){
        Cookie cookie = new Cookie("session", sessions.getId());
        cookie.setMaxAge(20 * 64 * 64);
        cookie.setPath("/");
        response.addCookie(cookie);
        System.out.println("Creating cookies for sessionId: " + sessions.getId());
        System.out.println("Max age of cookies: " + cookie.getMaxAge());

    }
}
