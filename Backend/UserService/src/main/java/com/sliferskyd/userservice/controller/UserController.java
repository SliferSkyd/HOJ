package com.sliferskyd.userservice.controller;

import com.sliferskyd.userservice.dto.UserRequest;
import com.sliferskyd.userservice.dto.UserResponse;
import com.sliferskyd.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/username/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}
