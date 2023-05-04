package com.example.instagram.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavePostDto {

    @NotBlank
    @Length(min = 1, max = 255)
    private String title;
    @NotBlank
    @Length(min = 1, max = 255)
    private String description;
    @NotBlank
    private String image;
}
