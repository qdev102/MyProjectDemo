package com.example.product_management.service.product;

import com.example.product_management.dto.ProductDto;
import com.example.product_management.model.Category;
import com.example.product_management.model.Product;
import com.example.product_management.request.AddProductRequest;
import com.example.product_management.request.ProductUpdateRequest;

import java.util.List;

public interface ProductI {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(Category category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleProductByID(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productID);


    List<ProductDto> getConvertedProduct(List<Product> product);

    ProductDto convertToDto(Product product);
}
