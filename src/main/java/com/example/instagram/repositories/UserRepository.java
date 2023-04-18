package com.example.instagram.repositories;

import com.example.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     User findByUserName(String username);

    boolean existsByUserName(String username);

    User saveProfileDto(User user1);
}
