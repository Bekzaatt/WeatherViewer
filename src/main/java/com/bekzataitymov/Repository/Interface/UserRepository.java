package com.bekzataitymov.Repository.Interface;

import com.bekzataitymov.Entity.User;

public interface UserRepository {

    User find(String login, String password);

    User findById(int id);

    User save(User user);
}
