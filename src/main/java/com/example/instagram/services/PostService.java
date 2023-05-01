package com.example.instagram.services;

import com.example.instagram.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost (Post post1, String userName);
    List<Post> getAllPosts();
    Post getByTitle(String title);
    Post updatePost (Post updatedPost, Long postId);
    Post deletePostByTitle(String title);
}
