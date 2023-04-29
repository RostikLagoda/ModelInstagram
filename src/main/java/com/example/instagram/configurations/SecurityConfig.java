package com.example.instagram.configurations;

import com.example.instagram.entity.enums.Role;
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
                                    .requestMatchers(HttpMethod.POST, "/user/registration").permitAll()
                                    .requestMatchers(HttpMethod.POST,"/user/auth").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/user/delete/user").hasAuthority(Role.ADMIN.getAuthority())
                                    .requestMatchers(HttpMethod.POST, "/post/save").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/post/get/allPost").permitAll()
                                    .requestMatchers(HttpMethod.GET, "/post/get/title").permitAll()
                                    .requestMatchers(HttpMethod.DELETE, "/post/deletePost").permitAll()
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
