package com.app.authentiScan.service.mapper;

import com.app.authentiScan.models.User;
import com.app.authentiScan.payload.request.SignupRequest;

public class UserMapper {
    public static User mapToUser(SignupRequest request) {
        return User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(request.getPassword())
                .build();
    }
}
