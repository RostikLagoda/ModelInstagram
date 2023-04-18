package com.example.instagram.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private String authorOfThePost;
    private String image;
}
