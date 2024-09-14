package com.hoya.restaurant.repository;

import com.hoya.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, String> {
    Optional<Menu> findByUuid(String uuid);
}
