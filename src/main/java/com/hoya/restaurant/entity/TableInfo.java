package com.hoya.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class TableInfo {

    @Id
    private String uuid;;

    private int tableNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public TableInfo() {
        this.uuid = UUID.randomUUID().toString();
    }
}
