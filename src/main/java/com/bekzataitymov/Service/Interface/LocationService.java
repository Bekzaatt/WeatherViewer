package com.bekzataitymov.Service.Interface;

import com.bekzataitymov.Entity.Locations;
import com.bekzataitymov.Entity.User;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface LocationService {
    List<Map<String, Object>> findLocations(String name);
    List<Locations> findAll();

    Locations findById(Locations locations);


    Locations save(Locations location);
    List<Map<String, Object>> getWeatherByLocations(List<Locations> location);

    Map<String, Object> getWeatherType(JsonNode weather);

    void delete(BigDecimal longitude, BigDecimal latitude, int userId);

    User getUser(String sessionsId);

    Locations findLocationByCoord(BigDecimal longitude, BigDecimal latitude, int userId);
}
