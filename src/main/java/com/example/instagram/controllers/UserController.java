package com.example.instagram.controllers;

import com.example.instagram.dto.user.ProfileDto;
import com.example.instagram.dto.user.ResponseUserDto;
import com.example.instagram.entity.User;
import com.example.instagram.entity.enums.Role;
import com.example.instagram.exception.UserException;
import com.example.instagram.services.UserService;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/save/user")
    public ResponseEntity<User> saveUser(@RequestBody ProfileDto profileDto) {
        User save = userService.savePerson(mapper.map(profileDto, User.class), Role.USER);
        return new ResponseEntity<>(mapper.map(save, User.class), HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/save/admin")
    public ResponseEntity<User> saveAdmin(@RequestBody ProfileDto profileDto) {
        User save = userService.savePerson(mapper.map(profileDto, User.class), Role.ADMIN);
        return new ResponseEntity<>(mapper.map(save, User.class), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> profile(Principal principal) {
        Optional<User> profile = userService.getByName(principal.getName());
        return new ResponseEntity<>(mapper.map(profile, ProfileDto.class), HttpStatus.OK);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<ProfileDto> updateProfile(@RequestBody ProfileDto profileDto){
        User updateProfile = userService.saveProfile(mapper.map(profileDto, User.class));
        return new ResponseEntity<>(mapper.map(updateProfile, ProfileDto.class), HttpStatus.OK) ;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user) {
        if (userService.existByName(user.getUsername())) {
            throw new UserException(String.format("This user %s already exists", user.getName()));
        } else {
           return ResponseEntity.ok( userService.savePerson(user, Role.USER));
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseUserDto> deleteByName(@PathVariable @Length(min = 1, max = 255) String username) {
        Optional<User> deletedUser = userService.deleteUser(username);
        return new ResponseEntity<>(mapper.map(deletedUser, ResponseUserDto.class), HttpStatus.OK);
    }
    @DeleteMapping("/admin/{username}")
    public ResponseEntity<ResponseUserDto> deleteByNameFromAdmin(@PathVariable @Length(min = 1, max = 255) String username) {
        Optional<User> deletedUser = userService.deleteUser(username);
        return new ResponseEntity<>(mapper.map(deletedUser, ResponseUserDto.class), HttpStatus.OK);
    }
}


