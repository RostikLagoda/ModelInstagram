package com.example.instagram.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDto {

    private long postId;
    private String title;
    private String description;
    private String authorOfThePost;
    private String image;
    private LocalDateTime dateCreating;
}
