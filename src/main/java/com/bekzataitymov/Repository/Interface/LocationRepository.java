package com.bekzataitymov.Repository.Interface;

import com.bekzataitymov.Entity.Locations;

import java.math.BigDecimal;
import java.util.List;

public interface LocationRepository {
    List<Locations> findAll();

    Locations findById(Locations locations);

    Locations save(Locations location);

    void delete(BigDecimal longitude, BigDecimal latitude, int userId);

    Locations findLocationByCoord(BigDecimal longitude, BigDecimal latitude, int userId);
}
