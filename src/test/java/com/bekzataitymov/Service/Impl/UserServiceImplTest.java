package com.bekzataitymov.Service.Impl;

import com.bekzataitymov.Service.Interface.UserServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@Transactional

public class UserServiceImplTest implements UserServiceTest {


    @Override
    public void saveTest() {
//        User user = new User();
//        String login = "Daniyar";
//        String password = "dani1234";
//        user.setLogin(login);
//        user.setPassword(password);
//        HttpServletResponse response = new MockHttpServletResponse();
//
//        User testUser = userService.save(login, password, response);
//        assertNotNull(testUser);
//        assertEquals(login, user.getLogin());
//
//        Sessions sessions = sessionsRepository.findByUser(user);
//        assertNotNull(sessions);
//
//        when(userRepository.save(anyString(), anyString())).thenReturn(user);
//        when(sessionsRepository.save(any(User.class))).thenReturn(sessions);
//
//        userService.save("Asylkhan", "asyl1234", response);
//
//        verify(userRepository, times(1)).save(anyString(), anyString());
//        verify(sessionsRepository, times(1)).save(any(User.class));
    }
}
