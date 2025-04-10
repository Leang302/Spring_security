package com.leang.jwtpractice.service.impl;

import com.leang.jwtpractice.model.entity.AppUser;
import com.leang.jwtpractice.model.request.AppUserRequest;
import com.leang.jwtpractice.model.response.AppUserResponse;
import com.leang.jwtpractice.repository.AppUserRepository;
import com.leang.jwtpractice.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.getUserByEmail(email);
    }
    @Override
    public AppUserResponse register(AppUserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        AppUser appUser = appUserRepository.register(request);
        for (String role : request.getRoles()){
            if (role.equals("ROLE_USER")){
                appUserRepository.insertUserIdAndRoleId(1L, appUser.getUserId());
            }
            if (role.equals("ROLE_ADMIN")){
                appUserRepository.insertUserIdAndRoleId(2L, appUser.getUserId());
            }
        }
        return modelMapper.map(appUserRepository.getUserById(appUser.getUserId()), AppUserResponse.class);
    }
}
