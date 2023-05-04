package com.example.instagram.services;

import com.example.instagram.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment add(Comment comment, Long postId);
    Comment deleteById(Long commentId);
    Page<Comment> getCommentsByPostId(Long postId, Pageable pageable);
    Comment edit(Long commentId, String description);
    Comment findById(Long commentId);
}
