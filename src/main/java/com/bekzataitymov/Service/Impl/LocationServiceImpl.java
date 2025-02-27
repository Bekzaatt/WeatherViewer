package com.bekzataitymov.Service.Impl;

import com.bekzataitymov.Entity.Locations;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;
import com.bekzataitymov.Repository.Interface.LocationRepository;
import com.bekzataitymov.Repository.Interface.SessionsRepository;
import com.bekzataitymov.Service.Interface.LocationService;
import com.bekzataitymov.Service.Interface.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@PropertySource("classpath:application.properties")
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private SessionsRepository sessionsRepository;
    @Autowired
    private UserService userService;
    private final int KELVIN_CONST = 273;
    @Value("${geoCodingUrl}")
    private String geoCodingUrl;
    @Value("${getWeatherUrl}")
    private String getWeatherUrl;
    @Value("${API_KEY}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Locations save(Locations location) {
        if(findById(location) == null) {
            return locationRepository.save(location);
        }else{
            throw new RuntimeException("There's such location already added");
        }
    }

    @Override
    public void delete(BigDecimal longitude, BigDecimal latitude, int userId) {
        locationRepository.delete(longitude, latitude, userId);
    }

    @Override
    public User getUser(String sessionsId) {
        User user = null;
        if(sessionsId != null) {
            Sessions sessions = sessionsRepository.findById(sessionsId);
            user = userService.findById(sessions.getUserId());
        }
        return user;
    }

    @Override
    public Locations findLocationByCoord(BigDecimal longitude, BigDecimal latitude, int userId) {
        return locationRepository.findLocationByCoord(longitude, latitude, userId);
    }

    @Override
    public List<Locations> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Locations findById(Locations locations) {
        return locationRepository.findById(locations);

    }

    @Override
    public List<Map<String, Object>> findLocations(String cityName) {
        String url = geoCodingUrl + "?q=" + cityName + "&limit=5" + "&appid=" + API_KEY;

        HttpEntity<Void> httpEntity = new HttpEntity<>(getHttpHeaders());
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<>() {});
            System.out.println("Response code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());

            return response.getBody();
        }catch (HttpClientErrorException exception){
            System.out.println("Exception caught: " + exception.getStatusCode());
            throw exception;
        }
    }

    @Override
    public List<Map<String, Object>> getWeatherByLocations(List<Locations> locations){
        BigDecimal lat = BigDecimal.valueOf(0);
        BigDecimal lon = BigDecimal.valueOf(0);
        List<Map<String, Object>> list = new ArrayList<>();
        for(Locations location : locations){
             lat = location.getLatitude();
             lon = location.getLongitude();
             String url = getWeatherUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&lang=en";
             HttpEntity<Void> httpEntity = new HttpEntity<>(getHttpHeaders());
             try {
                 ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JsonNode.class);
                 list.add(getWeatherType(response.getBody()));
             }catch (HttpClientErrorException exception){
                 throw exception;
             }

        }

        return list;

    }

    @Override
    public Map<String, Object> getWeatherType(JsonNode weather) {
        Map<String, Object> currentWeather = new LinkedHashMap<>();
        String descriptionFirst =  weather.get("weather").get(0).get("description").asText().substring(0, 1).toUpperCase();
        String description = weather.get("weather").get(0).get("description").asText().substring(1);

        currentWeather.put("Temperature", weather.get("main").get("temp").asInt() - KELVIN_CONST);
        currentWeather.put("name", weather.get("name").asText() + ", " + weather.get("sys").get("country").asText());
        currentWeather.put("Feels like", weather.get("main").get("feels_like").asInt() - KELVIN_CONST + "°C. " +
                descriptionFirst + description);
        currentWeather.put("lon", weather.get("coord").get("lon").asDouble());
        currentWeather.put("lat", weather.get("coord").get("lat").asDouble());

        return currentWeather;
    }

    private HttpHeaders getHttpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
