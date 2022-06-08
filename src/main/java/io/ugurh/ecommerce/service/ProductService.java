package io.ugurh.ecommerce.service;

import io.ugurh.ecommerce.dto.ProductDto;
import io.ugurh.ecommerce.model.Category;
import io.ugurh.ecommerce.model.Product;
import io.ugurh.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(ProductDto productDto, Category category) {
        Product newProduct = dtoToModel(productDto, category);
        productRepository.save(newProduct);
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(modelToDto(product));
        }

        return productDtos;
    }

    public void updateProduct(Long productId, ProductDto productDto, Category category) {
        Product product = dtoToModel(productDto, category);
        product.setId(productId);
        productRepository.save(product);
    }

    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    private static Product dtoToModel(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return product;
    }

    private ProductDto modelToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setPrice(product.getPrice());
        productDto.setName(product.getName());
        return productDto;
    }


}
