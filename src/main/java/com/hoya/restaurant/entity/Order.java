package com.hoya.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "`Order`")
public class Order {

    @Id
    private String uuid;

    private String uid;
    private String tableUuid;
    private String cartUuid;
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Order() {
        this.uuid = UUID.randomUUID().toString();
        this.status = OrderStatus.confirm;
    }
}
