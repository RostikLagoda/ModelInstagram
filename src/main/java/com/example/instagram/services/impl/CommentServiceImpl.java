package com.example.instagram.services.impl;

import com.example.instagram.entity.Comment;
import com.example.instagram.entity.Post;
import com.example.instagram.exception.CommentException;
import com.example.instagram.exception.PostException;
import com.example.instagram.repositories.CommentRepository;
import com.example.instagram.services.CommentService;
import com.example.instagram.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    @Override
    public Comment add(Comment comment, Long postId) {
        log.info(String.format("Request add comment by postId %s", postId));
        Post currentPost = postService.findById(postId);
        comment.setId(currentPost.getId());
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteById(Long commentId) {
        log.info(String.format("Request delete comment by commentId %s", commentId));
        Comment commentById = findById(commentId);
        commentRepository.delete(commentById);
        return commentById;
    }

    @Override
    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageable) {
        log.info(String.format("Request list Comments by postId %s", postId));
        postService.findById(postId);
        Page<Comment> allByPostId = commentRepository.findAllByPostId(postId,pageable);
        if (allByPostId.isEmpty()) {
            throw new PostException("Comments is empty");
        } else {
            return allByPostId;
        }
    }

    @Override
    public Comment edit(Long commentId, String commentText) {
        log.info(String.format("Request edit Comments by commentId %s", commentId));
        Comment commentById = findById(commentId);
        commentById.setCommentText(commentText);
        commentRepository.save(commentById);
        return commentById;
    }

    @Override
    public Comment findById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(String.format("Comment %s not found",commentId)));
    }
}
