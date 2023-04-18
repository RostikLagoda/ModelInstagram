package com.example.instagram.services;

import com.example.instagram.dto.ProfileDto;
import com.example.instagram.entity.User;


import java.util.List;

public interface UserService {

    User saveUser(String username, String password);
    List<User> getAll();
    User saveAdmin(String username, String password);
    User getByUserName(String userName);

    ProfileDto mapUserToProfileDto(User user1);

    void mapProfileDtoToUser(User user1, ProfileDto profileDto);

    User saveProfile(User user1);

    boolean existByUserName(String username);
}
