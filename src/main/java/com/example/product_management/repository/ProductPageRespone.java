package com.example.product_management.repository;

import com.example.product_management.dto.ProductDto;

import java.util.List;

public record ProductPageRespone(List<ProductDto> productDtos,
                                  int pageNumber,
                                  Integer pageSize,
                                  int totalElements,
                                  int totalPages,
                                  boolean isLast) {

}
