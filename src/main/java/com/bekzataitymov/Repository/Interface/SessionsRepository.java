package com.bekzataitymov.Repository.Interface;

import com.bekzataitymov.Entity.DTO.UserDTO;
import com.bekzataitymov.Entity.Sessions;
import com.bekzataitymov.Entity.User;

public interface SessionsRepository {
    Sessions save(User user);
    Sessions findByUser(User user);
    Sessions update(Sessions sessions);
    Sessions findById(String id);

    void delete(String sessionsId);
}
