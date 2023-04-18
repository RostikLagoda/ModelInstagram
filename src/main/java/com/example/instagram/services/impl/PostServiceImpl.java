package com.example.instagram.services.impl;

import com.example.instagram.dto.PostDto;
import com.example.instagram.entity.Post;
import com.example.instagram.repositories.PostRepository;
import com.example.instagram.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(String title, String description, String authorOfThePost, String image) {
        Post post = Post.builder()
                .title(title)
                .description(description)
                .authorOfThePost(authorOfThePost)
                .image(image)
                .build();
        return postRepository.savePost(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getByTitle(String title) {
       return postRepository.findByTitle(title);
    }

    @Override
    public ResponseEntity<?> deletePostByTitle(String title) {
        postRepository.deletePostByTitle(title);
        return null;
    }

    @Override
    public PostDto mapPostToPostDto(Post post1) {
        return PostDto.builder()
                .title(post1.getTitle())
                .description(post1.getDescription())
                .authorOfThePost(post1.getAuthorOfThePost())
                .image(post1.getImage())
                .build();
    }

    @Override
    public void mapPostDtoToPost(Post post1, PostDto PostDto) {
        post1.setTitle(PostDto.getTitle());
        post1.setDescription(PostDto.getDescription());
        post1.setAuthorOfThePost(PostDto.getAuthorOfThePost());
        post1.setImage(PostDto.getImage());
    }

    @Override
    public Post savePostDto(Post post1) {
        return postRepository.savePostDto(post1);
    }
}

