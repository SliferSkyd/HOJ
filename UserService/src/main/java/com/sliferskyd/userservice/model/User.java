package com.sliferskyd.userservice.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String password;
}
