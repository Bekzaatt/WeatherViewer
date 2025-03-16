package com.bekzataitymov.Controller;

import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.Locations;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.ExceptionHandler.CityNotFoundException;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import com.bekzataitymov.Service.Interface.LocationService;
import com.bekzataitymov.Service.Interface.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LocationController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    SessionsRepository sessionsRepository;

    @GetMapping({"/weather", "/"})
    public String weathersViewer(Model model){
        List<Locations> locations = locationService.findAll();
        List<Map<String, Object>> totalLocations = null;
        if(locations != null) {
            totalLocations = locationService.getWeatherByLocations(locations);
        }
        model.addAttribute("locations", totalLocations);
        return "main-page.html";
    }

    @GetMapping("/locations")
    public String findLocations(@RequestParam("name") String name, @CookieValue(name = "session", required = false)
                                    String sessionsId, Model model) {

        UserDTO user = locationService.getUser(sessionsId);
        if(name == null || name.isEmpty()){
            model.addAttribute("user", user);
            return weathersViewer(model);
        }
        List<Map<String, Object>> locations = locationService.findLocations(name);

        model.addAttribute("locations", locations);
        model.addAttribute("name", name);
        model.addAttribute("user", user);

        return "searchResult.html";
    }

    @PostMapping("/addLocation")
    public String saveLocation(@RequestParam("lon") String lon,
                                       @RequestParam("lat") BigDecimal lat,
                                       @RequestParam("name") String name, @RequestParam("UserId") int id, Model model
    ){
        Locations locations = new Locations(name, id, lat, new BigDecimal(lon));
        locationService.save(locations);

        UserDTO user = userService.findById(id);
        model.addAttribute("user", user);
        return weathersViewer(model);
    }

    @GetMapping("/deleteLocation")
    public String delete(@RequestParam("lon") BigDecimal longitude, @RequestParam("lat") BigDecimal latitude,
                         @CookieValue(name = "session", required = false) String sessionsId, Model model){
        Sessions sessions = sessionsRepository.findById(sessionsId);
        locationService.delete(longitude, latitude, sessions.getUserId());
        UserDTO user = userService.findById(sessions.getUserId());
        model.addAttribute("user", user);

        return weathersViewer(model);
    }
}
