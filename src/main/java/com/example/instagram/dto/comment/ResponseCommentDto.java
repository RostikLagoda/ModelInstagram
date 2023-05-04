package com.example.instagram.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDto {

    private long id;
    private long postId;
    private String authorOfPost;
    private String commentText;
    private LocalDateTime dateCreating;
}
