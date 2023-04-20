package com.example.instagram.services;

import com.example.instagram.dto.PostDto;
import com.example.instagram.entity.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    Post savePost (String title, String description, String authorOfThePost, String image);
    List<Post> getAllPosts();
    Post getByTitle(String title);
    ResponseEntity<?> deletePostByTitle(String title);
//    PostDto mapPostToPostDto(Post post1);
//    void mapPostDtoToPost(Post post1, PostDto PostDto);
   // Post savePostDto(Post post11);
}
