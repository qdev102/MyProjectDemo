package com.example.product_management.dto;

import com.example.product_management.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImagineDto> images;
}
