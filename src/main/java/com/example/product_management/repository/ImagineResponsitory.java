package com.example.product_management.repository;

import com.example.product_management.model.Imagine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagineResponsitory extends JpaRepository<Imagine, Long> {
    List<Imagine> findByProductId(Long id);

}
