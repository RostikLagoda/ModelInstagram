package com.example.instagram.configurations;

import com.example.instagram.entity.User;
import com.example.instagram.entity.enums.Role;
import com.example.instagram.exception.UserException;
import com.example.instagram.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> {
                            auth.requestMatchers(HttpMethod.POST, "/user/save/user").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/user/get/all").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/user/save/admin").hasAuthority(Role.ADMIN.getAuthority())
                                    .requestMatchers(HttpMethod.GET, "/user/profile").permitAll()
                                    .requestMatchers(HttpMethod.PUT, "/user/update/profile").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/user/registration").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/user/auth").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/user/{username}").hasAuthority(Role.ADMIN.getAuthority())
                                    .requestMatchers(HttpMethod.POST, "/post/save/post").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/post/get/allPost").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/post/get/title").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/post/deletePost").permitAll()
                                    .requestMatchers(HttpMethod.PUT, "/post/update/post").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/comment/add/{postId}").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/comment/{commentId}").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/comment/{postId}/getAll").permitAll()
                                    .requestMatchers(HttpMethod.PUT, "/comment/edit/{commentId}").permitAll()
                                    .anyRequest()
                                    .authenticated();
                        }
                )
                .csrf().disable()
                .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
