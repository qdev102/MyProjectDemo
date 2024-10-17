package com.example.product_management.repository;

import com.example.product_management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

    public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByName(String role);
    }

