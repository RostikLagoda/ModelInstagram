package com.example.instagram.services.impl;

import com.example.instagram.entity.Comment;
import com.example.instagram.entity.Post;
import com.example.instagram.exception.CommentException;
import com.example.instagram.exception.PostException;
import com.example.instagram.exception.UserException;
import com.example.instagram.repositories.CommentRepository;
import com.example.instagram.repositories.PostRepository;
import com.example.instagram.repositories.UserRepository;
import com.example.instagram.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment add(Comment comment, long postId) {
        log.info(String.format("Request add comment by postId %s", postId));
        if(userRepository.findByUserName(comment.getAuthorOfPost()) == null){
            throw new UserException("User not found");
        }
        userRepository.findByUserName(comment.getAuthorOfPost());
        Post currentPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException(String.format("Post %s not found", postId)));
        comment.setPostId(currentPost.getId());
        return commentRepository.save(comment);
    }

    @Override
    public Comment delete(long commentId) {
        log.info(String.format("Request delete comment by commentId %s", commentId));
        Comment commentById = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(String.format("Comment %s not found",commentId)));
        commentRepository.delete(commentById);
        return commentById;
    }

    @Override
    public Page<Comment> getCommentsByPostId(long postId, Pageable pageable) {
        log.info(String.format("Request list Comments by postId %s", postId));
        postRepository.findById(postId)
                .orElseThrow(() -> new PostException(String.format("Post %s not found",postId)));
        Page<Comment> allByPostId = commentRepository.findAllByPostId(postId,pageable);
        if (allByPostId.isEmpty()) {
            throw new PostException("Comments is empty");
        } else {
            return allByPostId;
        }
    }

    @Override
    public Comment edit(long commentId, String commentText) {
        log.info(String.format("Request edit Comments by commentId %s", commentId));
        Comment commentById = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(String.format("Comment %s not found", commentId)));
        commentById.setCommentText(commentText);
        commentRepository.save(commentById);
        return commentById;
    }
}
