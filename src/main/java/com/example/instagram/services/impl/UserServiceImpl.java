package com.example.instagram.services.impl;

import com.example.instagram.entity.User;
import com.example.instagram.entity.enums.Role;
import com.example.instagram.entity.enums.UserStatus;
import com.example.instagram.exception.PostException;
import com.example.instagram.exception.UserException;
import com.example.instagram.repositories.CommentRepository;
import com.example.instagram.repositories.PostRepository;
import com.example.instagram.repositories.UserRepository;
import com.example.instagram.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Request save User {}", user.getUsername());
        if (userRepository.existsByUserName(user.getUsername())) {
            throw new UserException(String.format("User with username %s not found", user.getUsername()));
        }
        User user1 = User.builder()
                .status(UserStatus.ACTIVE)
                .userName(user.getUsername())
                .userPassword(passwordEncoder.encode(user.getPassword()))
                .roleSet(Set.of(Role.USER))
                .build();
        return userRepository.save(user1);
    }

    @Override
    public User saveAdmin(User user) {
        log.info("Request save Admin {}", user.getUsername());
        if (userRepository.existsByUserName(user.getUsername())) {
            throw new UserException(String.format("Admin with username %s not found", user.getUsername()));
        }
        User user1 = User.builder()
                .status(UserStatus.ACTIVE)
                .userName(user.getUsername())
                .userPassword(passwordEncoder.encode(user.getPassword()))
                .roleSet(Set.of(Role.ADMIN))
                .build();
        return userRepository.save(user1);
    }

    @Override
    public User saveProfile(User user) {
        log.info("Request save profile {}", user.getUsername());
        if(userRepository.existsByUserName(user.getUsername())) {
            throw new UserException(String.format("Admin with username %s not found", user.getUsername()));
        }
        User user1 = User.builder()
                .email(user.getEmail())
                .country(user.getCountry())
                .numberPhone(user.getNumberPhone())
                .build();
        return userRepository.save(user1);
    }



    @Override
    public List<User> getAllUsers() {
        log.info("Request All User");
        return Optional.of(userRepository.findAll())
                .orElseThrow(() -> new PostException("Users not found"));
    }

    @Override
    public User getByUserName(String userName) {
        log.info("Request get User by user name {}", userName);
        if(userRepository.existsByUserName(userName)){
            throw new UserException(String.format("User with username %s not found", userName));
        }
        return userRepository.findByUserName(userName);
    }

    @Override
    public User deleteUser(String userName){
        log.info("Request delete {}", userName);
        if(userRepository.existsByUserName(userName)){
            throw new UserException("User not found");
        }
       User user1 = userRepository.findByUserName(userName);
       userRepository.deleteUserByUserName(userName);
        postRepository.deleteAllByAuthorOfThePost(userName);
        commentRepository.deleteAllByAuthorOfPost(userName);
        return user1;
    }

    @Override
    public boolean existByUserName(String userName){
      return   userRepository.existsByUserName(userName);
    }
}
