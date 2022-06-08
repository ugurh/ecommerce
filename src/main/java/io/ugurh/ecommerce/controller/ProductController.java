package io.ugurh.ecommerce.controller;

import io.ugurh.ecommerce.common.ApiResponse;
import io.ugurh.ecommerce.dto.ProductDto;
import io.ugurh.ecommerce.model.Category;
import io.ugurh.ecommerce.model.Product;
import io.ugurh.ecommerce.service.CategoryService;
import io.ugurh.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductService productService;
    CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> category = categoryService.readCategory(productDto.getCategoryId());

        if (category.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }

        productService.create(productDto, category.get());

        return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> body = productService.getAllProducts();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId,
                                                     @RequestBody @Valid ProductDto productDto) {

        Optional<Product> product = productService.getProduct(productId);
        if (product.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "product is invalid"), HttpStatus.CONFLICT);
        }

        Optional<Category> category = categoryService.readCategory(productDto.getCategoryId());
        if (category.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
        }

        productService.updateProduct(productId, productDto, category.get());
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }
}
