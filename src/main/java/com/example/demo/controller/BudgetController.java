package com.example.demo.controller;

import com.example.demo.model.Budget;
import com.example.demo.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("budget")
public class BudgetController {
    @Autowired
    BudgetService budgetService;



    @GetMapping("/")
    public String index() {
        return "Checking budget Endpoint!";
    }
    @PostMapping("addBudget/")
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.createBudget(budget);
    }

    @GetMapping("/user/{userId}")
    public List<Budget> getBudgetsByUser(@PathVariable Long userId) {
        return budgetService.getBudgetsByUser(userId);
    }

    @PutMapping("/update/Budget")
    public List<Budget> updateBudget(@RequestBody Budget budget){
        return budgetService.updateBudget(budget);
    }

    @GetMapping("/budgets")
    public List<Budget> getAllBudget() {
        return budgetService.getAllBudget();
    }
}
