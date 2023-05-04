package com.example.instagram.services;

import com.example.instagram.entity.User;
import com.example.instagram.entity.enums.Role;


import java.util.List;
import java.util.Optional;

public interface UserService {

    User savePerson(User user, Role role);
    List<User> getAllUsers();
    User saveProfile(User user);
    Optional<User> getByName(String userName);
    Optional<User> deleteUser(String userName);
    boolean existByName(String userName);
    Optional<User> findByName(String userName);
}
