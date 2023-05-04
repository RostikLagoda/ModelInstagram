package com.example.instagram.services.impl;

import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.exception.PostException;
import com.example.instagram.repositories.PostRepository;
import com.example.instagram.services.CommentService;
import com.example.instagram.services.PostService;
import com.example.instagram.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public Post savePost(Post post1, Principal principal) {
        log.info(String.format("Request Post %s save by %s", post1.getTitle(), principal.getName()));
        Optional<User> byUserName = userService.getByName(principal.getName());
        Post.PostBuilder builder = Post.builder();
        builder.title(post1.getTitle());
        builder.description(post1.getDescription());
        builder.image(post1.getImage());
        builder.user(byUserName.get());
        Post post = builder
                .build();
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        log.info("Request All Posts");
        return Optional.of(postRepository.findAll())
                .orElseThrow(() -> new PostException("Posts not found"));
    }

    @Override
    public Post updatePost (Post updatedPost, Long postId) {
        log.info(String.format("Request update Post by id %s", postId));
        Post postById = findById(postId);
        if (postById.getUser().getName().equals(updatedPost.getUser().getName())) {
           setPostFields(postById,updatedPost);
            return postRepository.save(postById);
        } else {
            throw new PostException(String.format("Post id%s not found", postId));
        }
    }

    @Override
    public Post getByTitle(String title) {
        log.info("Request get Post by {}", title);
        return Optional.ofNullable(postRepository.findByTitle(title))
                .orElseThrow(() -> new PostException(String.format("Post id %s not found", title)));
    }

    @Override
    public Post deletePostByTitle(String title) {
        log.info("Request delete {}", title);
        Post postById =Optional.ofNullable(postRepository.findByTitle(title))
                .orElseThrow(() -> new PostException(String.format("Post title %s not found", title)));
        postRepository.delete(postById);
        return postById;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostException(String.format("Post id %s not found", id)));
    }

    private void setPostFields(Post postById, Post updatedPost){
        postById.setTitle(updatedPost.getTitle());
        postById.setDescription(updatedPost.getDescription());
        postById.setImage(updatedPost.getImage());
    }
}

