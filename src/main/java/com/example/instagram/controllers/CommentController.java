package com.example.instagram.controllers;


import com.example.instagram.dto.comment.GetCommentDto;
import com.example.instagram.dto.comment.ResponseCommentDto;
import com.example.instagram.dto.comment.SaveCommentDto;
import com.example.instagram.dto.comment.UpdateCommentDto;
import com.example.instagram.entity.Comment;
import com.example.instagram.services.CommentService;
import com.example.instagram.services.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper mapper;

    @Autowired
    public CommentController(CommentServiceImpl commentService, ModelMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<ResponseCommentDto> addComment(@PathVariable @Positive long postId, @Valid @RequestBody SaveCommentDto saveCommentDto) {
        Comment add = commentService.add(mapper.map(saveCommentDto, Comment.class), postId);
        return new ResponseEntity<>(mapper.map(add, ResponseCommentDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> deleteComment(@PathVariable @Positive long commentId) {
        Comment delete = commentService.deleteById(commentId);
        return new ResponseEntity<>(mapper.map(delete, ResponseCommentDto.class), HttpStatus.OK);
    }

    @GetMapping("/{postId}/get/comment")
    public ResponseEntity<List<ResponseCommentDto>> getCommentsByPostId(@PathVariable @Positive long postId,
                                                            @RequestBody GetCommentDto getCommentDto) {
        List<ResponseCommentDto> commentDto = commentService.getCommentsByPostId(postId, (PageRequest.of(getCommentDto.getPage().orElse(0),
                                                                getCommentDto.getSize().orElse(5),
                                                                Sort.Direction.ASC,
                                                                getCommentDto.getSortBy().orElse("postId"))))
                                                    .stream()
                                                    .map(comment -> mapper.map(comment, ResponseCommentDto.class))
                                                    .collect(Collectors.toList());
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable @Positive long commentId, @Valid @RequestBody UpdateCommentDto updateComment) {
        Comment edit = commentService.edit(commentId, updateComment.getDescription());
        return new ResponseEntity<>(mapper.map(edit, ResponseCommentDto.class), HttpStatus.OK);
    }
}
