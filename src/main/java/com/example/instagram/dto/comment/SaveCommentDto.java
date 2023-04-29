package com.example.instagram.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class SaveCommentDto {

    @NotBlank
    @Length(min = 1, max = 40)
    private String authorOfPost;
    @NotBlank
    @Length(min = 1, max = 255)
    private String commentText;
}
