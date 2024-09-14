package com.hoya.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoya.restaurant.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUid(String uid);
}
