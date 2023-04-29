package com.example.instagram.services;

import com.example.instagram.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment add(Comment comment, long postId);
    Comment delete(long commentId);
    Page<Comment> getCommentsByPostId(long postId, Pageable pageable);
    Comment edit(long commentId, String description);
}
