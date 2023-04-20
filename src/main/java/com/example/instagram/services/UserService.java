package com.example.instagram.services;

import com.example.instagram.entity.User;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface UserService {

    User saveUser(String userName, String password);
    List<User> getAll();
    User saveAdmin(String userName, String password);
    User getByUserName(String userName);
//    ProfileDto mapUserToProfileDto(User user1);
//    void mapProfileDtoToUser(User user1, ProfileDto profileDto);
    //User saveProfile(User user1);
    boolean existByUserName(String userName);
    ResponseEntity<?> deleteUser(String userName);
}
