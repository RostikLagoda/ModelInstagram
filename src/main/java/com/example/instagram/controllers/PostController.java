package com.example.instagram.controllers;

import com.example.instagram.entity.Post;
import com.example.instagram.services.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/save")
    public ResponseEntity<Post> savePost(@RequestParam String title, @RequestParam String description,
                                   @RequestParam String authorOfThePost, @RequestParam String image){
      return ResponseEntity.ok(postService.savePost(title, description, authorOfThePost, image));
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Post>> getAll(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/get/title")
    public ResponseEntity<Post> getByTitle(@RequestParam String title){
        return ResponseEntity.ok(postService.getByTitle(title));
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<?> deleteByTitle(@RequestParam String title){
        return postService.deletePostByTitle(title);
    }
}
