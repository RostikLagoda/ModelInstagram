package com.example.instagram.services;

import com.example.instagram.dto.post.SavePostDto;
import com.example.instagram.entity.Post;

import java.util.List;

public interface PostService {

    Post savePost (SavePostDto post1, String userName);
    List<Post> getAllPosts();
    Post getByTitle(String title);
    void deletePostByTitle(String title);
}
