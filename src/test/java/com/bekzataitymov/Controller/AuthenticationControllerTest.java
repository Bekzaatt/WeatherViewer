package com.bekzataitymov.Controller;

import com.bekzataitymov.Config.DatabaseConfigTest;
import com.bekzataitymov.Config.FlywayConfigTest;
import com.bekzataitymov.DatabaseConfig.FlywayConfig;
import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import com.bekzataitymov.Service.Interface.UserService;
import com.bekzataitymov.Util.ModelMapper;
import com.bekzataitymov.WebMVCConfig.WebConfig;
import jakarta.servlet.http.Cookie;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WebConfig.class, DatabaseConfigTest.class, FlywayConfigTest.class})
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthenticationControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionsRepository sessionsRepository;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private DataSource dataSource;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        try {
            System.out.println("Hibernate database URL: " + dataSource.getConnection().getMetaData().getURL());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void registerTest() throws Exception {
        User user = new User();
        user.setUsername("Asylkhan");
        user.setPassword("asyl1234");

        mockMvc.perform(post("/register")
                .param("login", "Asylkhan")
                .param("password", "asyl1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"));


        UserDTO testUser = userService.find(user.getUsername(), user.getPassword(), new MockHttpServletResponse());
        assertNotNull(testUser);
        assertEquals("Asylkhan", testUser.getUsername());
        assertTrue( BCrypt.checkpw("asyl1234", testUser.getPassword()));

        Sessions sessions = sessionsRepository.findByUser(ModelMapper.convertDtoToEntity(testUser, User.class));

        assertNotNull(sessions);
        assertEquals(testUser.getId(), sessions.getUserId());

    }

    @Test
    void loginTest() throws Exception {
        User user = new User();
        user.setUsername("Asylkhan");
        user.setPassword("asyl1234");
        userService.save(user.getUsername(), user.getPassword(), new MockHttpServletResponse());

        UserDTO testUser = userService.find(user.getUsername(), user.getPassword(), new MockHttpServletResponse());

        mockMvc.perform(post("/login").param("login", user.getUsername()).param("password", user.getPassword()))
                .andExpect(status().isOk()).andExpect(view().name("main-page.html"));

        assertNotNull(testUser);
        assertEquals("Asylkhan", testUser.getUsername());
        assertTrue(BCrypt.checkpw("asyl1234", testUser.getPassword()));

        Sessions sessions = sessionsRepository.findByUser(ModelMapper.convertDtoToEntity(testUser, User.class));

        assertNotNull(sessions);
        assertEquals(testUser.getId(), sessions.getUserId());
    }

    @Test
    void logoutTest() throws Exception {
        User user = new User();
        user.setUsername("Asylkhan");
        user.setPassword("asyl1234");

        userService.save(user.getUsername(), user.getPassword(), new MockHttpServletResponse());

        UserDTO testUser = userService.find(user.getUsername(), user.getPassword(), new MockHttpServletResponse());
        Sessions sessions = sessionsRepository.findByUser(ModelMapper.convertDtoToEntity(testUser, User.class));
        System.out.println(sessions.getId());
        mockMvc.perform(get("/logout").cookie(new Cookie("session", sessions.getId()))).andExpect(status().isOk()).andExpect(view().name("login.html"));

        Sessions sessionsTest = sessionsRepository.findById(sessions.getId());

        assertNull(sessionsTest);

    }

    @Test
    void UniqueUserExceptionTest(){
        User user = new User();
        user.setUsername("Asylkhan");
        user.setPassword("asyl1234");
        userService.save(user.getUsername(), user.getPassword(),new MockHttpServletResponse());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.save(user.getUsername(), user.getPassword(), new MockHttpServletResponse()));

        assertEquals("Попробуйте ввести другой логин или пароль", exception.getMessage());
    }
}
