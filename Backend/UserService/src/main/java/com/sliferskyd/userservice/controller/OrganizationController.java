package com.sliferskyd.userservice.controller;

import com.sliferskyd.userservice.dto.OrganizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    @PostMapping("/")
    public OrganizationResponse getOrganizations() {
        return null;
    }

    @PostMapping("/register")
    public void registerOrganization() {
    }

    @PostMapping("/delete")
    public void deleteOrganization() {
    }

    @PostMapping("/update")
    public void updateOrganization() {
    }
}
