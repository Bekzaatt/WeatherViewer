package com.bekzataitymov.Service.Impl;

import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import com.bekzataitymov.Repository.Interface.UserRepository;
import com.bekzataitymov.Service.Interface.UserService;
import com.bekzataitymov.Util.ModelMapper;
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
    public UserDTO save(String username, String password, HttpServletResponse response) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepository.save(user);
        Sessions sessions = sessionsRepository.save(user);
        setCookies(sessions, response);

        return ModelMapper.convertEntityToDto(user, UserDTO.class);
    }

    @Override
    public UserDTO findByCredentials(String username, String password, HttpServletResponse response) {
        User user = userRepository.find(username, password);
        if(user != null){
            return ModelMapper.convertEntityToDto(userRepository.find(username, password), UserDTO.class);
        }
        return null;
    }

    @Override
    public UserDTO find(String username, String password, HttpServletResponse response) throws UserPrincipalNotFoundException {
        User user = userRepository.find(username, password);
        if (user == null){
            return null;
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
        return ModelMapper.convertEntityToDto(user, UserDTO.class);
    }


    @Override
    public UserDTO findById(int id) {

        return ModelMapper.convertEntityToDto(userRepository.findById(id), UserDTO.class);
    }

    @Override
    public void logout(String sessionsId, HttpServletResponse response) {
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
        cookie.setMaxAge(1800);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
