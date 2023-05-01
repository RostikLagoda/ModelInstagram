package com.example.instagram.services.impl;

import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.exception.PostException;
import com.example.instagram.repositories.CommentRepository;
import com.example.instagram.repositories.PostRepository;
import com.example.instagram.services.PostService;
import com.example.instagram.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

   @Autowired
   private  UserService userService;

    @Override
    public Post savePost(Post post1, String userName) {
        log.info(String.format("Request Post %s save by %s", post1.getTitle(), userName));
        User byUserName = userService.getByUserName(userName);
        Post post = Post.builder()
                .title(post1.getTitle())
                .description(post1.getDescription())
                .image(post1.getImage())
                .build();
        post1.setAuthorOfThePost(byUserName.getUsername());
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        log.info("Request All Posts");
        return Optional.of(postRepository.findAll())
                .orElseThrow(() -> new PostException("Posts not found"));
    }

    public Post updatePost (Post updatedPost, Long postId) {
        log.info(String.format("Request update Post by id %s", postId));
        Post postById = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(String.format("Post id%s not found", postId)));
        if (postById.getAuthorOfThePost().equals(updatedPost.getAuthorOfThePost())) {
            postById.setTitle(updatedPost.getTitle());
            postById.setDescription(updatedPost.getDescription());
            postById.setImage(updatedPost.getImage());
            return postRepository.save(postById);
        } else {
            throw new RuntimeException(String.format("Post id%s not found", postId));
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
                .orElseThrow(() -> new RuntimeException(String.format("Post title %s not found", title)));
        commentRepository.deleteAllByPostId(postById.getId());
        postRepository.delete(postById);
        return postById;
    }
}

