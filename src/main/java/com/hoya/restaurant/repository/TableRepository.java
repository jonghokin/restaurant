package com.hoya.restaurant.repository;

import com.hoya.restaurant.entity.TableInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableInfo, String> {
}
