package com.example.product_management.repository;

import com.example.product_management.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryResponsitory extends JpaRepository <Category, Long> {

    Category findByName(String name);

    boolean existsByName(String name);
}
