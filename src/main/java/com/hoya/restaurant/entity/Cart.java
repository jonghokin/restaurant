package com.hoya.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@IdClass(CartId.class)
public class Cart {

    @Id
    private String uuid;

    @Id
    private String menuUuid;

    private String uid;

    private String tableUuid;

    private int quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Cart() {
        this.uuid = UUID.randomUUID().toString();
    }
}
