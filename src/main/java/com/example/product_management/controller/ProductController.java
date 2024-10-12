package com.example.product_management.controller;

import com.example.product_management.dto.ProductDto;
import com.example.product_management.exception.ResourceNotFoundException;
import com.example.product_management.model.Product;
import com.example.product_management.request.AddProductRequest;
import com.example.product_management.request.ProductUpdateRequest;
import com.example.product_management.response.ApiResponse;
import com.example.product_management.service.category.CategoryI;
import com.example.product_management.service.product.ProductI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final ProductI productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProduct = productService.getConvertedProduct(products);
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

    @GetMapping("products/{pid}/product")
    public ResponseEntity<ApiResponse> getProductByID(@PathVariable Long pid) {
        try {
            Product product = productService.getProductById(pid);
            ProductDto productDto = productService.convertToDto(product);

            return ResponseEntity.ok(new ApiResponse("Found!!", product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theproduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product success!!", theproduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("products/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productID) {
        try {
            Product theproduct = productService.updateProduct(request, productID);
            ProductDto productDto = productService.convertToDto(theproduct);

            return ResponseEntity.ok(new ApiResponse("Update product success", theproduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("products/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleProductByID(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

//    @GetMapping("/products/by/brand-and-name")
//    public ResponseEntity<ApiResponse> getProductByBrandName(@RequestParam String brandName, @RequestParam String productName){
//        try {
//            List<Product> products = productService.getProductsByCategoryAndBrand(brandName, productName );
//            if (products.isEmpty()) {
//                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
//            }
//            return  ResponseEntity.ok(new ApiResponse("success", products));
//        } catch (Exception e) {
//            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
//        }
//    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}

