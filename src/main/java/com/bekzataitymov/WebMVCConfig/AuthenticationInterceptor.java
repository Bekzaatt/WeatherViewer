package com.bekzataitymov.WebMVCConfig;

import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private SessionsRepository sessionsRepository;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = null;
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                }
            }
        }


        if(sessionId == null){
            response.sendRedirect(req.getContextPath() + "/login");
            return false;
        }

        Sessions sessions = sessionsRepository.findById(sessionId);

        if (sessions != null && sessions.getExpiresAt().isAfter(LocalDateTime.now())){
            sessionsRepository.update(sessions);
            Cookie cookie = new Cookie("session", sessions.getId());
            cookie.setMaxAge(20);
            response.addCookie(cookie);
            return true;
        } else {
            response.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
    }
}
