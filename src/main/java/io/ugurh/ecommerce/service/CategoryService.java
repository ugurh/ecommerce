package io.ugurh.ecommerce.service;

import io.ugurh.ecommerce.model.Category;
import io.ugurh.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category readCategory(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> listCategories() {
        return  categoryRepository.findAll();
    }

    public void updateCategory(Long categoryId, Category category) {
        Category categoryDB = categoryRepository.findById(categoryId).get();
        categoryDB.setCategoryName(category.getCategoryName());
        categoryDB.setDescription(category.getDescription());
        categoryDB.setImageUrl(category.getImageUrl());
        categoryRepository.save(category);
    }

    public Optional<Category> readCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
