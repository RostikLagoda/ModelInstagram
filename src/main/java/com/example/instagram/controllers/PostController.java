package com.example.instagram.controllers;

import com.example.instagram.dto.comment.ResponseCommentDto;
import com.example.instagram.dto.post.ResponsePostDto;
import com.example.instagram.dto.post.SavePostDto;
import com.example.instagram.entity.Post;
import com.example.instagram.entity.User;
import com.example.instagram.exception.PostException;
import com.example.instagram.services.PostService;
import com.example.instagram.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/post")
@Validated
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public PostController(PostService postService, UserService userService, ModelMapper mapper) {
        this.postService = postService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseCommentDto> savePost(@RequestBody SavePostDto savePostDto, Principal principal){
      Post post = postService.savePost(mapper.map(savePostDto, Post.class), principal);
      return new ResponseEntity<>(mapper.map(post, ResponseCommentDto.class), HttpStatus.OK);
    }

    @GetMapping("/allPost")
    public ResponseEntity<List<Post>> getAll(){
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{title}")
    public ResponseEntity<Post> getByTitle( @PathVariable String title){
        Post post = postService.getByTitle(title);
               return ResponseEntity.ok(post);
    }

    @DeleteMapping("/delete/{title}")
    public ResponseEntity<ResponsePostDto> deleteByTitle(Principal principal,@PathVariable String title){
        Optional<User> user =userService.getByName(principal.getName());
        Post post = postService.deletePostByTitle(title);
        if(!user.equals(post.getUser())){
            throw new PostException("У вас недостаточно прав");
        }
        return new ResponseEntity<>(mapper.map(post, ResponsePostDto.class), HttpStatus.OK);
    }

    @PutMapping("/updatePost")
    public ResponseEntity<ResponsePostDto> updatePost(@RequestBody Post updatedPost, @RequestParam Long postId){
        Post post = postService.updatePost(updatedPost, postId);
         return new ResponseEntity<>(mapper.map(post, ResponsePostDto.class), HttpStatus.OK);
    }
}
