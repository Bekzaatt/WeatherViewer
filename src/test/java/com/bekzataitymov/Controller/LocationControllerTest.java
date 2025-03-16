package com.bekzataitymov.Controller;

import com.bekzataitymov.Config.DatabaseConfigTest;
import com.bekzataitymov.Config.FlywayConfigTest;
import com.bekzataitymov.Config.WebConfigTest;
import com.bekzataitymov.DatabaseConfig.FlywayConfig;
import com.bekzataitymov.Entity.Locations;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.LocationRepository;
import com.bekzataitymov.Service.Interface.LocationService;
import com.bekzataitymov.Service.Interface.UserService;
import com.bekzataitymov.WebMVCConfig.AuthenticationInterceptor;
import com.bekzataitymov.WebMVCConfig.WebConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, DatabaseConfigTest.class, FlywayConfigTest.class})
@ExtendWith(SpringExtension.class)
public class LocationControllerTest {
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @MockitoBean
    private AuthenticationInterceptor interceptor;

    @BeforeEach
    void setUp() throws Exception {

        Mockito.when(interceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void findLocationsTest() throws Exception {
        String geoCodingUrl = "https://api.openweathermap.org/geo/1.0/direct";
        String cityName = "London";
        String API_KEY = "5b210f7f2889becb855abf6916278a1f";
        String url = geoCodingUrl + "?q=" + cityName + "&limit=5" + "&appid=" + API_KEY + "&lang=en";
        List<Map<String, Object>> response = locationService.findLocations("London");
        mockMvc.perform(get("/locations")
                .param("Search", cityName)
                .param("session", "bekz"))
                .andExpect(status().isOk())
                .andExpect(view().name("searchResult.html"))
                .andExpect(model().attribute("locations", response));


    }
    @Test
    void saveLocationTest() throws Exception {
        User user = new User();
        user.setUsername("Asylkhan");
        user.setPassword("asyl12334");
        userService.save(user.getUsername(), user.getPassword(), new MockHttpServletResponse());

        mockMvc.perform(post("/addLocation")
                        .param("lon", "10.99")
                        .param("lat", "44.34")
                        .param("name", "London")
                        .param("UserId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"));
    }
    @Test
    void findAllLocationTest() throws Exception {
        List<Locations> locations = locationService.findAll();
        List<Map<String, Object>> totalLocations = null;
        if(locations != null) {
            totalLocations = locationService.getWeatherByLocations(locations);
        }

        mockMvc.perform(get("/weather"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"))
                .andExpect(model().attribute("locations", totalLocations));
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"))
                .andExpect(model().attribute("locations", totalLocations));
    }
    @Test
    void deleteLocationTest() throws Exception {
        Locations locations = new Locations("London", 1, BigDecimal.valueOf(44.34), BigDecimal.valueOf(10.99));

        locationService.save(locations);
        mockMvc.perform(get("/deleteLocation/{userId}", 1L)
                .param("lon", "10.99")
                .param("lat", "44.34"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page.html"));

    }

}
