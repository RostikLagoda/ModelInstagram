package com.example.instagram.dto.comment;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponseCommentDto {

    private long id;
    private long postId;
    private String authorOfPost;
    private String commentText;
    private LocalDateTime dateCreating;
}
