package com.example.instagram.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    @NotBlank
    @Length(min = 1, max = 40)
    private String name;
    @NotBlank
    @Length(min = 1, max = 30)
    private String password;
    @NotBlank
    @Length(min = 1, max = 40)
    @Email
    private String email;
    @NotBlank
    @Length(min = 1, max = 30)
    private String country;
    @NotBlank
    @Length(min = 1, max = 30)
    private String numberPhone;
}
