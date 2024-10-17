package com.example.product_management.service;

import com.example.product_management.dto.ImagineDto;
import com.example.product_management.dto.ProductDto;
import com.example.product_management.exception.AlreadyExitException;
import com.example.product_management.exception.ProductNotFoundException;
import com.example.product_management.model.Category;
import com.example.product_management.model.Imagine;
import com.example.product_management.model.Product;
import com.example.product_management.repository.CategoryResponsitory;
import com.example.product_management.repository.ImagineResponsitory;
import com.example.product_management.repository.ProductPageRespone;
import com.example.product_management.repository.ProductReponsitory;
import com.example.product_management.request.AddProductRequest;
import com.example.product_management.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductReponsitory productReponsitory;
    private final CategoryResponsitory categoryResponsitory;
    private final ModelMapper modelMapper;
    private final ImagineResponsitory imageRepository;


    public List<Product> getAllProducts() {
        return productReponsitory.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productReponsitory.findByCategoryName(category);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productReponsitory.findByBrand(brand);
    }

    public List<Product> getProductsByCategoryAndBrand(Category category, String brand) {
        return List.of();
    }

////
//    @Override
//    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
//        return productReponsitory.getProductsByCategoryAndBrand(category, brand);
//    }

    public List<Product> getProductsByName(String name) {
        return productReponsitory.findByName(name);
    }

    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productReponsitory.findByBrandAndName(brand, name);
    }

    public Long countProductsByBrandAndName(String brand, String name) {
        return productReponsitory.countByBrandAndName(brand, name);
    }

    public Product addProduct(AddProductRequest rq) {
        //check if category found in DB
        //if yes , set is as new in DB
        //if no, save it like a new category
        //the set as new product cate
        if (productExists(rq.getName(), rq.getBrand())) {
            throw new AlreadyExitException(rq.getBrand() + " "
                    + rq.getName() + " already exists, you may update this product instead!");
        }
        Category category = Optional.ofNullable(categoryResponsitory.findByName(rq.getCategory().getName()))
                .orElseGet(() ->
                {
                    Category newCategory = new Category(rq.getCategory().getName());
                    return categoryResponsitory.save(newCategory);
                });
        rq.setCategory(category);
        return productReponsitory.save(createProduct(rq, category));
    }
    private boolean productExists(String name, String brand) {
        return productReponsitory.existsByNameAndBrand(name, brand);
    }
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    public Product getProductById(Long id) {
        return productReponsitory.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    public void deleProductByID(Long id) {
        if (!productReponsitory.existsById(id)) {
            throw new ProductNotFoundException("product not found");
        }
        productReponsitory.deleteById(id);
    }


    public Product updateProduct(ProductUpdateRequest request, Long productID) {
        return productReponsitory.findById(productID)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productReponsitory::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryResponsitory.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;

    }

    public List<ProductDto> getConvertedProduct(List<Product> product) {
        return product.stream().map(this::convertToDto).toList();
    }

    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Imagine> images = imageRepository.findByProductId(product.getId());
        List<ImagineDto> imagineDtos = images.stream()
                .map(imagine -> modelMapper.map(imagine, ImagineDto.class)).toList();
        productDto.setImages(imagineDtos);
        return productDto;
    }

    public ProductPageRespone getAllProductsWithPagination(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productReponsitory.findAll(pageable);
        List<ProductDto> productDto = getConvertedProduct(productPage.getContent());

        return new ProductPageRespone(productDto, pageNumber, pageSize,
                productPage.getTotalPages(),
                (int) productPage.getTotalElements(),
                productPage.isLast());
    }

    public ProductPageRespone getAllProductsWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy,
                                                                     String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> productPage = productReponsitory.findAll(pageable);
        List<ProductDto> productDto = getConvertedProduct(productPage.getContent());

        return new ProductPageRespone(productDto, pageNumber, pageSize,
                productPage.getTotalPages(),
                (int) productPage.getTotalElements(),
                productPage.isLast());
    }
}
