package com.example.instagram.services;

import com.example.instagram.dto.user.ProfileDto;
import com.example.instagram.entity.User;


import java.util.List;

public interface UserService {

    User saveUser(User user);
    List<User> getAllUsers();
    User saveProfile(User user);
    User saveAdmin(User user);
    User getByUserName(String userName);
    User deleteUser(String userName);
    boolean existByUserName(String userName);
}
