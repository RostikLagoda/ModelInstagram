package com.example.instagram.services;

import com.example.instagram.entity.Post;

import java.security.Principal;
import java.util.List;

public interface PostService {

    Post savePost (Post post1, Principal principal);
    List<Post> getAllPosts();
    Post getByTitle(String title);
    Post updatePost (Post updatedPost, Long postId);
    Post deletePostByTitle(String title);
    Post findById(Long id);
}
