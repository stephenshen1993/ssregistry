package com.stephenshen.ssregistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * wrap exception response.
 * @author stephenshen
 * @date 2024/5/24 07:46:48
 */
@AllArgsConstructor
@Data
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String message;
}
