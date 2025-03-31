package com.leang.jwtpractice.service;

import com.leang.jwtpractice.model.request.AppUserRequest;
import com.leang.jwtpractice.model.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AppUserService extends UserDetailsService {
    AppUserResponse register(AppUserRequest request);
}
