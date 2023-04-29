package com.example.instagram.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponsePostDto {

    private long postId;
    private String title;
    private String description;
    private String authorOfThePost;
    private String image;
    private LocalDateTime dateCreating;
}
