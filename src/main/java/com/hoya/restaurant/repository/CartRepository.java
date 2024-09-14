package com.hoya.restaurant.repository;

import com.hoya.restaurant.entity.Cart;
import com.hoya.restaurant.entity.CartId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, CartId> {

    List<Cart> findAllByUuid(String uuid);

    Optional<Cart> findByMenuUuidAndUuid(String menuUuid, String uuid);

}
