package com.example.instagram.controllers;


import com.example.instagram.dto.comment.ResponseCommentDto;
import com.example.instagram.dto.comment.SaveCommentDto;
import com.example.instagram.dto.comment.UpdateCommentDto;
import com.example.instagram.entity.Comment;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add/{postId}")
    public ResponseEntity<?> add(@PathVariable @Positive long postId, @Valid @RequestBody SaveCommentDto saveCommentDto) {
        Comment add = commentService.add(mapper.map(saveCommentDto, Comment.class), postId);
        return new ResponseEntity<>(mapper.map(add, ResponseCommentDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> delete(@PathVariable @Positive long commentId) {
        Comment delete = commentService.delete(commentId);
        return new ResponseEntity<>(mapper.map(delete, ResponseCommentDto.class), HttpStatus.OK);
    }

    @GetMapping("/{postId}/getAll")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable @Positive long postId,
                                                 @RequestParam Optional<Integer> page,
                                                 @RequestParam Optional<Integer> size,
                                                 @RequestParam Optional<String> sortBy) {
        List<ResponseCommentDto> commentDto = commentService.getCommentsByPostId(postId, (PageRequest.of(page.orElse(0),
                        size.orElse(5),
                        Sort.Direction.ASC,
                        sortBy.orElse("postId"))))
                .stream()
                .map(comment -> mapper.map(comment, ResponseCommentDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{commentId}")
    public ResponseEntity<?> edit(@PathVariable @Positive long commentId, @Valid @RequestBody UpdateCommentDto updateComment) {
        Comment edit = commentService.edit(commentId, updateComment.getDescription());
        return new ResponseEntity<>(mapper.map(edit, ResponseCommentDto.class), HttpStatus.OK);
    }
}
