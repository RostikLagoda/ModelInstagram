package com.example.instagram.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {

    private long id;

    private String name;

    private String password;

    private String email;

    private String country;

    private String numberPhone;
}
