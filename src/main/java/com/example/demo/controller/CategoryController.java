package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("category")
class CategoryController {
    @Autowired
    CategoryServices categoryServices;

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return categoryServices.findById(id);
    }

    @PostMapping("addCategory")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryServices.createCategory(category));
    }

    // Get Categories by User ID
    @GetMapping("/income_category/{userId}")
    public ResponseEntity<List<Category>> getIncomeCategoriesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryServices.getIncomeCategoriesByUser(userId));
    }

    // Get Categories by User ID
    @GetMapping("/expense_category/{userId}")
    public ResponseEntity<List<Category>> getExpenseCategoriesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryServices.getExpenseCategoriesByUser(userId));
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryServices.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
