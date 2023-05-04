package com.example.instagram.controllers;

import com.example.instagram.exception.CommentException;
import com.example.instagram.exception.PostException;
import com.example.instagram.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final String text = "Bad request by %s";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleMethodUserException(UserException UserEx){
        log.warn(String.format(text, UserEx.getMessage()));
        return new ResponseEntity<>(UserEx.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<Object> handleMethodPostException(PostException PostEx){
        log.warn(String.format(text, PostEx.getMessage()));
        return new ResponseEntity<>(PostEx.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<Object> handleMethodCommentException(CommentException CommentEx){
        log.warn(String.format(text, CommentEx.getMessage()));
        return new ResponseEntity<>(CommentEx.getMessage(), HttpStatus.BAD_REQUEST);
    }
    }
















