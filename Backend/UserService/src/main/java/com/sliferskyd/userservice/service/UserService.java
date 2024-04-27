package com.sliferskyd.userservice.service;

import com.sliferskyd.userservice.dto.UserRequest;
import com.sliferskyd.userservice.dto.UserResponse;
import com.sliferskyd.userservice.model.User;
import com.sliferskyd.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    public void registerUser(UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .build();
        userRepository.save(user);
        // Register a new user
        log.info("Registering a new user with username: {}", userRequest.getUsername());
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        // Get a user by username
        log.info("Getting a user with username: {}", username);
        return UserResponse.builder()
                .username(user.getUsername())
                .build();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        // Get a user by id
        log.info("Getting a user with id: {}", id);
        return UserResponse.builder()
                .username(user.getUsername())
                .build();
    }

    public List<UserResponse> getUsers() {
        // Get all users
        log.info("Getting all users");
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .username(user.getUsername())
                        .build())
                .toList();
    }
}