package com.bekzataitymov.Controller;

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
    @GetMapping({"/weather", "/"})
    public String weathersViewer(Model model){
        List<Locations> locations = locationService.findAll();
        System.out.println("Locations from weatherViewer: " + locations);
        List<Map<String, Object>> totalLocations = null;
        if(locations != null) {
            totalLocations = locationService.getWeatherByLocations(locations);
        }
        model.addAttribute("locations", totalLocations);
        return "main-page.html";
    }

    @GetMapping("/locations")
    public String findLocations(@RequestParam("Search") String name, @CookieValue(name = "session", required = false) String sessionsId,
                           Model model) {
        User user = locationService.getUser(sessionsId);

        if(name == null || name.isEmpty()){
            model.addAttribute("user", user);
            return weathersViewer(model);
        }
        List<Map<String, Object>> locations = locationService.findLocations(name);

        model.addAttribute("locations", locations);
        model.addAttribute("search", name);
        model.addAttribute("user", user);

        return "searchResult.html";
    }

    @PostMapping("/addLocation")
    public String saveLocation(@RequestParam("lon") String lon,
                                       @RequestParam("lat") String lat,
                                       @RequestParam("name") String name, @RequestParam("UserId") int id, Model model
    ){
        Locations locations = new Locations(name, id, BigDecimal.valueOf(Double.parseDouble(lat)), BigDecimal.valueOf(Double.parseDouble(lon)));
        locationService.save(locations);

        User user = userService.findById(id);
        model.addAttribute("user", user);
        return weathersViewer(model);
    }

    @GetMapping("/deleteLocation/{userId}")
    public String delete(@RequestParam("lon") BigDecimal longitude, @RequestParam("lat") BigDecimal latitude,
                         @PathVariable(value = "userId", required = false) int userId, Model model){
        locationService.delete(longitude, latitude, userId);
        User user = userService.findById(userId);
        model.addAttribute("user", user);

        return weathersViewer(model);
    }
}
