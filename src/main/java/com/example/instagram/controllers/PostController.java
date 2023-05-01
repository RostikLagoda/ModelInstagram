package com.example.instagram.controllers;

import com.example.instagram.dto.comment.ResponseCommentDto;
import com.example.instagram.dto.post.ResponsePostDto;
import com.example.instagram.dto.post.SavePostDto;
import com.example.instagram.entity.Post;
import com.example.instagram.services.impl.PostServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public ResponseEntity<?> savePost(@RequestBody SavePostDto savePostDto, @RequestParam String userName){
      Post post = postService.savePost(mapper.map(savePostDto, Post.class), userName);
      return new ResponseEntity<>(mapper.map(post, ResponseCommentDto.class), HttpStatus.OK);
    }

    @GetMapping("/get/allPost")
    public ResponseEntity<List<Post>> getAll(Model model){
        if(postService.getAllPosts().isEmpty()){
            model.addAttribute("message", "Постов нету" );
        }
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/get/title")
    public ResponseEntity<Post> getByTitle(@RequestParam String title, Model model){
        if(postService.getByTitle(title) ==null){
            model.addAttribute("message", "акого поста не существует");
        }
        return ResponseEntity.ok(postService.getByTitle(title));
    }

    @DeleteMapping("/delete/post")
    public ResponseEntity<?> deleteByTitle(@RequestParam String title){
        Post post = postService.deletePostByTitle(title);
        return new ResponseEntity<>(mapper.map(post, ResponsePostDto.class), HttpStatus.OK);
    }

    @PutMapping("/update/post")
    public ResponseEntity<?> updatePost(@RequestBody Post updatedPost, @RequestParam Long postId){
        Post post = postService.updatePost(updatedPost, postId);
         return new ResponseEntity<>(mapper.map(post, ResponsePostDto.class), HttpStatus.OK);
    }
}
