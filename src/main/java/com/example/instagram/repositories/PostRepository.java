package com.example.instagram.repositories;

import com.example.instagram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  //  Post savePostDto(Post post1);
    void deletePostByTitle(String title);
    Post findByTitle(String title);
}
