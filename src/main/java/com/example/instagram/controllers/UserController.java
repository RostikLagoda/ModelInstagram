package com.example.instagram.controllers;

import com.example.instagram.dto.user.ProfileDto;
import com.example.instagram.dto.user.ResponseUserDto;
import com.example.instagram.entity.User;
import com.example.instagram.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/save/user")
    public ResponseEntity<User> saveUser(@RequestBody ProfileDto profileDto) {
        User save = userService.saveUser(mapper.map(profileDto, User.class));
        return new ResponseEntity<>(mapper.map(save, User.class), HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<User>> getAll(Model model) {
        if(userService.getAllUsers().isEmpty()){
            model.addAttribute("message", "Пользователи не найдены");
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/save/admin")
    public ResponseEntity<User> saveAdmin(@RequestBody ProfileDto profileDto) {
        User save = userService.saveAdmin(mapper.map(profileDto, User.class));
        return new ResponseEntity<>(mapper.map(save, User.class), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestBody User user1, @RequestBody ProfileDto profileDto, Model model) {
        if(userService.existByUserName(user1.getUsername())){
            model.addAttribute("message", "Пользаватель не найден");
        }
        User profile = userService.saveProfile(mapper.map(profileDto, User.class));
        return new ResponseEntity<>(mapper.map(profile, User.class), HttpStatus.OK);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user, @RequestBody ProfileDto profileDto, Model model){
        if(userService.existByUserName(user.getUsername())){
            model.addAttribute("massege", "Профиль не найден");
        }
        User updateProfile = userService.saveProfile(mapper.map(profileDto, User.class));
        return new ResponseEntity<>(mapper.map(updateProfile, User.class), HttpStatus.OK) ;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody User user, Model model) {
        if (userService.existByUserName(user.getUsername())) {
            model.addAttribute("message", "Пользаватель уже существует");
        } else {
           return ResponseEntity.ok( userService.saveUser(user));
        }
        return ResponseEntity.ok(userService.getByUserName(user.getUsername()));
    }

    @PostMapping("/auth")
    public ResponseEntity<User> authorization(@RequestParam String username,@RequestParam String password, Model model, HttpSession mod) {
        if (!userService.existByUserName(username)) {
            model.addAttribute("message", "Пользователя не существует.");
        } else {
            User user = userService.getByUserName(username);
            if (user.getPassword().equals(password)) {
               mod.setAttribute("user", user);
            } else {
                model.addAttribute("message", "Данные введены не правильно.");
            }
        }
        return ResponseEntity.ok(userService.getByUserName(username));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteByName(@PathVariable @Length(min = 1, max = 255) String username) {
        User deletedUser = userService.deleteUser(username);
        return new ResponseEntity<>(mapper.map(deletedUser, ResponseUserDto.class), HttpStatus.OK);
    }
}


