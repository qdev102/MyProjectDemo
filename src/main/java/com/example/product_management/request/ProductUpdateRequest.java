package com.example.product_management.request;

import com.example.product_management.model.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
