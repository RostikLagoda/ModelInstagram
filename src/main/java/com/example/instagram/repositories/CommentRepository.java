package com.example.instagram.repositories;

import com.example.instagram.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByPostId(Long id);
    void deleteAllByAuthorOfPost(String creatorUsername);
    Page<Comment> findAllByPostId(long postId, Pageable pageable);
}
