package com.example.instagram.services.impl;

import com.example.instagram.dto.ProfileDto;
import com.example.instagram.entity.User;
import com.example.instagram.entity.enums.Role;
import com.example.instagram.exception.UserNotFoundEx;
import com.example.instagram.repositories.UserRepository;
import com.example.instagram.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }

    @Override
    public User saveUser(String username, String password) {
        if (userRepository.existsByUserName(username)) {
            throw new UserNotFoundEx(String.format("User with username %s not found", username));
        }

        User user = User.builder()
                .userName(username)
                .userPassword(passwordEncoder.encode(password))
                .roleSet(Set.of(Role.USER))
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveAdmin(String username, String password) {
        if (userRepository.existsByUserName(username)) {
            throw new UserNotFoundEx(String.format("User with username %s not found", username));
        }

        User user = User.builder()
                .userName(username)
                .userPassword(passwordEncoder.encode(password))
                .roleSet(Set.of(Role.ADMIN))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


    @Override
    public ProfileDto mapUserToProfileDto(User user1) {
        return ProfileDto.builder()

                .userName(user1.getUsername())
                .userPassword(user1.getUserPassword())
                .email(user1.getEmail())
                .country(user1.getCountry())
                .numberPhone(user1.getNumberPhone())
                .build();
    }

    public void mapProfileDtoToUser(User user1, ProfileDto profileDto) {
        user1.setUserName(profileDto.getUserName());
        user1.setUserPassword(profileDto.getUserPassword());
        user1.setEmail(profileDto.getEmail());
        user1.setCountry(profileDto.getCountry());
        user1.setNumberPhone(profileDto.getNumberPhone());
    }

    public User saveProfile(User user1) {
        return userRepository.saveProfileDto(user1);
    }

    @Override
    public boolean existByUserName(String username) {
        return userRepository.existsByUserName(username);
    }
}
