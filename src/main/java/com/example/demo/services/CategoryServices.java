package com.example.demo.services;

import com.example.demo.model.Category;
import com.example.demo.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServices {

    @Autowired
    CategoryRepository categoryRepository;

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    
    public List<Category> getIncomeCategoriesByUser(Long userId) {
        return categoryRepository.findByUserIdAndTransactionTypeTrue(userId);
    }

    public List<Category> getExpenseCategoriesByUser(Long userId) {
        return categoryRepository.findByUserIdAndTransactionTypeFalse(userId);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
