package com.hoya.restaurant.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    private String uid;

    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User() {
        this.role = UserRole.user;
    }
}
