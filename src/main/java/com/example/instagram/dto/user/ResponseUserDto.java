package com.example.instagram.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponseUserDto {

    private long id;

    private String userName;

    private String userPassword;

    private String email;

    private String country;

    private String numberPhone;
}
