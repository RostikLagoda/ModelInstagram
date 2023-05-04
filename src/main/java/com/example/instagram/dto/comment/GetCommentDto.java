package com.example.instagram.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentDto {

     private Optional<Integer> page;
    private Optional<Integer> size;
    private Optional<String> sortBy;
}
