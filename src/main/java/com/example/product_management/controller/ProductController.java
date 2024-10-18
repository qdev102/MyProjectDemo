package com.example.product_management.controller;

import com.example.product_management.dto.ProductDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.Product;
import com.example.product_management.repository.ProductPageRespone;
import com.example.product_management.repository.ProductReponsitory;
import com.example.product_management.request.AddProductRequest;
import com.example.product_management.request.ProductUpdateRequest;
import com.example.product_management.response.ApiResponse;
import com.example.product_management.service.ProductService;
//import com.example.product_management.service.category.CategoryI;
//import com.example.product_management.service.product.ProductI;
import com.example.product_management.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")

public class ProductController {
    private final ProductService productService; // Thay ProductI bằng ProductService

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProduct(products);
        return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("/allProductsPage")
    public ResponseEntity<ApiResponse> getProductsWithPagination(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize) {
        ProductPageRespone productPageResponse = productService.getAllProductsWithPagination(pageNumber, pageSize);
        return ResponseEntity.ok(new ApiResponse(" successfully", productPageResponse));
    }

    @GetMapping("/allProductsSort")
    public ResponseEntity<ApiResponse> getProductsWithPaginationAndSorting(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir) {
        ProductPageRespone productPageResponse = productService.getAllProductsWithPaginationAndSorting(pageNumber, pageSize, sortBy, dir);
        return ResponseEntity.ok(new ApiResponse("successfully", productPageResponse));
    }

    @GetMapping("products/{pid}/product")
    public ResponseEntity<ApiResponse> getProductByID(@PathVariable Long pid) throws ResourceNotFoundException {
        Product product = productService.getProductById(pid);
        ProductDto productDto = productService.convertToDto(product);
        return ResponseEntity.ok(new ApiResponse("Found!!", productDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        Product theProduct = productService.addProduct(product);
        return ResponseEntity.ok(new ApiResponse("Add product success!!", theProduct));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("products/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) throws ResourceNotFoundException {
        Product theProduct = productService.updateProduct(request, productId);
        ProductDto productDto = productService.convertToDto(theProduct);
        return ResponseEntity.ok(new ApiResponse("Update product success", productDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("products/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ResourceNotFoundException {
        productService.deleProductByID(productId);
        return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        var productCount = productService.countProductsByBrandAndName(brand, name);
        return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
    }
}