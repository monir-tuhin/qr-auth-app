package com.app.authentiScan.utils;

import com.app.authentiScan.utils.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseFactory {

    public <T> ResponseEntity<ApiResponse<T>> saveResponse(String message) {
        return new ResponseEntity<>(
                new ApiResponse<T>(null, HttpStatus.CREATED.value(), Boolean.TRUE, message),
                new HttpHeaders(), HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> saveResponse(T t, String message) {
        return new ResponseEntity<>(
                new ApiResponse<T>(t, HttpStatus.CREATED.value(), Boolean.TRUE, message),
                new HttpHeaders(), HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> updateResponse(String message) {
        return new ResponseEntity<>(
                new ApiResponse<T>(null, HttpStatus.OK.value(), Boolean.TRUE, message),
                new HttpHeaders(), HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> updateResponse(T t, String message) {
        return new ResponseEntity<>(
                new ApiResponse<T>(t, HttpStatus.OK.value(), Boolean.TRUE, message),
                new HttpHeaders(), HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> deleteResponse(String message) {
        return new ResponseEntity<>(
                new ApiResponse<T>(null, HttpStatus.OK.value(), Boolean.TRUE, message),
                new HttpHeaders(), HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> getResponse(T t) {
        return new ResponseEntity<>(
                new ApiResponse<T>(t, HttpStatus.OK.value(), Boolean.TRUE, "Data fetched successfully"),
                new HttpHeaders(), HttpStatus.OK);
    }

    public <T> ResponseEntity<ApiResponse<T>> errorResponse(String message, Integer statusCode) {
        return new ResponseEntity<>(
                new ApiResponse<T>(null, statusCode, Boolean.FALSE, message),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
