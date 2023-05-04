package com.example.instagram.services.impl;

import com.example.instagram.entity.User;
import com.example.instagram.entity.enums.Role;
import com.example.instagram.entity.enums.UserStatus;
import com.example.instagram.exception.UserException;
import com.example.instagram.repositories.UserRepository;
import com.example.instagram.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    @Lazy
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User savePerson(User user, Role role) {
        log.info("Request save User {}", user.getUsername());
        if (userRepository.existsByName(user.getUsername())) {
            throw new UserException(String.format("This user %s already exists", user.getUsername()));
        }
        User user1 = User.builder()
                .status(UserStatus.ACTIVE)
                .name(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roleSet(Set.of(role))
                .build();
        return userRepository.save(user1);
    }

    @Override
    public User saveProfile(User user) {
        log.info("Request save profile {}", user.getUsername());
        if(userRepository.existsByName(user.getUsername())) {
            throw new UserException(String.format("This user %s with profile already exists", user.getUsername()));
        }
        User userDb = userRepository.findByName(user.getName());
        if(userDb.getName().equals(user.getName())){
            setProfileFields(userDb, user);
            return userRepository.save(userDb);
        }else{
            throw new UserException("The names don't match");
        }

    }

    @Override
    public List<User> getAllUsers() {
        log.info("Request All User");
        return Optional.of(userRepository.findAll())
                .orElseThrow(() -> new UserException("Users not found"));
    }

    @Override
    public Optional<User> getByName(String userName) {
        log.info("Request get User by user name {}", userName);
        return findByName(userName);
    }

    @Override
    public Optional<User> deleteUser(String userName){
        log.info("Request delete {}", userName);
       Optional<User> user1 = findByName(userName);
       userRepository.deleteUserByName(userName);
        return user1;
    }

    @Override
    public boolean existByName(String userName){
      return   userRepository.existsByName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if(user==null){
            throw new UserException(String.format("User with username %s not found", username));
        }
        return user;
    }

    @Override
    public Optional<User> findByName(String userName) {
       return Optional.ofNullable(Optional.ofNullable(userRepository.findByName(userName))
               .orElseThrow(() -> new UserException(String.format("User with username %s not found", userName))));
    }

    private void setProfileFields(User oldUser, User newUser){
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setCountry(newUser.getCountry());
        oldUser.setNumberPhone(newUser.getNumberPhone());
    }
}
