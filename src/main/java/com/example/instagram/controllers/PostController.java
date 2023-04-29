package com.example.instagram.controllers;

import com.example.instagram.dto.post.SavePostDto;
import com.example.instagram.entity.Post;
import com.example.instagram.services.impl.PostServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/post")
@Validated
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/save/post")
    public ResponseEntity<?> savePost(@RequestBody SavePostDto post, @RequestParam String userName){

      return ResponseEntity.ok(postService.savePost(post, userName));
    }

    @GetMapping("/get/allPost")
    public ResponseEntity<List<Post>> getAll(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/get/title")
    public ResponseEntity<Post> getByTitle(@RequestParam String title){
        return ResponseEntity.ok(postService.getByTitle(title));
    }

    @DeleteMapping("/deletePost")
    public void deleteByTitle(@RequestParam String title){
         postService.deletePostByTitle(title);
    }

    @PutMapping("/update/post")
    public ResponseEntity<?> update(@RequestBody Post updatedPost, @RequestParam Long postId){
         return ResponseEntity.ok(postService.update(updatedPost, postId));
    }
}
