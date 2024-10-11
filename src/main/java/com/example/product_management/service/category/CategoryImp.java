package com.example.product_management.service.category;

import com.example.product_management.exception.AlreadyExitException;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.Category;
import com.example.product_management.repository.CategoryResponsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryImp implements CategoryI{
    private final CategoryResponsitory categoryResponsitory;

    @Override
    public Category getCategoryById(Long id) {
        return categoryResponsitory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not foud"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryResponsitory.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryResponsitory.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryResponsitory.existsByName(c.getName()))
                .map(categoryResponsitory :: save).orElseThrow(() -> new AlreadyExitException(category.getName()+"already exist!"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryResponsitory.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryResponsitory.findById(id)
                .ifPresentOrElse(categoryResponsitory::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });
    }
}
