package com.example.instagram.repositories;

import com.example.instagram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByTitle(String title);
    void deleteAllByAuthorOfThePost(String creatorUsername);
}
