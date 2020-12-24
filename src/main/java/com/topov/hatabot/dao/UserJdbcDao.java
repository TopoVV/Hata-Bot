package com.topov.hatabot.dao;

import java.util.Optional;

public interface UserJdbcDao {
    Optional<User> getUserById(String userId);
    void saveUser(User user);
    void activateUser(String userId);
}
