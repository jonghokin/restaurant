package com.hoya.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Menu {

    @Id
    private String uuid;

    private String name;
    private int price;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Menu() {
        this.uuid = UUID.randomUUID().toString();
    }

}
