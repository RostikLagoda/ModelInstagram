package com.example.instagram.controllers;

import com.example.instagram.dto.ProfileDto;
import com.example.instagram.entity.User;
import com.example.instagram.services.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestParam String username,
                                     @RequestParam String password) {
        return ResponseEntity.ok(userService.saveUser(username, password));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/save/admin")
    public ResponseEntity<User> saveAdmin(@RequestParam String username,
                                          @RequestParam String password) {
        return ResponseEntity.ok(userService.saveAdmin(username, password));
    }

    @GetMapping("/profile")
    public ResponseEntity<Model> profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        User user1 = userService.getByUserName(user.getUsername());
//        ProfileDto profileDto = userService.mapUserToProfileDto(user1);
        return (ResponseEntity<Model>) model.addAttribute("user",user);
    }

//    @PostMapping("/profile")
//    public ResponseEntity<Model> profile(@ModelAttribute(value = "user") ProfileDto profileDto, Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        User user1 = userService.getByUserName(user.getUsername());
//        userService.mapProfileDtoToUser(user1, profileDto);
//        userService.saveProfile(user1);
//        session.setAttribute("user", user1);
//        model.addAttribute("message", "Обновление произошло успешно");
//        return (ResponseEntity<Model>) model.addAttribute("user", profileDto);
//    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(String username, String password, Model model) {
        if (userService.existByUserName(username)) {
            model.addAttribute("message", "Пользаватель уже существует");
        } else {
           return ResponseEntity.ok( userService.saveUser(username, password));
        }
        return ResponseEntity.ok(userService.getByUserName(username));
    }

    @PostMapping("/auth")
    public ResponseEntity<User> authorization(String username, String password, Model model, HttpSession mod) {
        if (!userService.existByUserName(username)) {
            model.addAttribute("message", "Пользователя не существует");
        } else {
            User user = userService.getByUserName(username);
            if (user.getPassword().equals(password)) {
               mod.setAttribute("user", user);
            } else {
                model.addAttribute("message", "Данные введены не правильно");
            }
        }
        return ResponseEntity.ok(userService.getByUserName(username));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteByName(@RequestParam String userName){
        return userService.deleteUser(userName);
    }
}


