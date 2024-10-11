package com.example.product_management.service.category;

import com.example.product_management.model.Category;

import java.util.List;

public interface CategoryI {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategoryById(Long id);
}
