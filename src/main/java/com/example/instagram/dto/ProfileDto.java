package com.example.instagram.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {

    private String userName;
    private String userPassword;
    private String email;
    private String country;
    private String numberPhone;
}
