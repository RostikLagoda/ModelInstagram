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

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> {
                            auth.requestMatchers(HttpMethod.POST, "/user/save/user").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.GET, "/user/get/all").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.POST, "/user/save/admin").hasAuthority(Role.ADMIN.getAuthority())
                                    .requestMatchers(HttpMethod.GET, "/user/profile").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.PUT, "/user/update/profile").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.POST, "/user/registration").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/user/{username}").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.DELETE, "/user/admin/{username}").hasAuthority(Role.ADMIN.getAuthority())
                                    .requestMatchers(HttpMethod.POST, "/post/save").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.GET, "/post/allPost").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.GET, "/post/{title}").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.DELETE, "/post/delete/{title}").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.PUT, "/post/updatePost").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.POST, "/comment/add/{postId}").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.DELETE, "/comment/{commentId}").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.GET, "/comment//{postId}/get/comment").hasAuthority(Role.USER.getAuthority())
                                    .requestMatchers(HttpMethod.PUT, "/comment/edit/{commentId}").hasAuthority(Role.USER.getAuthority())
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
