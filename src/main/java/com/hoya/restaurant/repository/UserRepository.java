package com.hoya.restaurant.repository;

import com.hoya.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
