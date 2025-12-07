package com.example.demo.services;

import com.example.demo.model.Budget;
import com.example.demo.repo.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class BudgetService {
    @Autowired
    BudgetRepository budgetRepository;

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUser(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    public List<Budget> updateBudget(Budget budget) {
        if (budget.getId() == null) {
            // You can throw an exception or handle as "create" instead
            throw new IllegalArgumentException("Budget id is required for update");
        }

        Budget existing = budgetRepository.findById(budget.getId())
                .orElseThrow(() -> new IllegalArgumentException("Budget not found with id: " + budget.getId()));

        // Update fields (only ones you want to be editable)
        existing.setAmount(budget.getAmount());
        existing.setStartDate(budget.getStartDate());
        existing.setEndDate(budget.getEndDate());
        existing.setTreshold(budget.getTreshold());
        existing.setNote(budget.getNote());
        // If userId should not change, skip this line
        existing.setUserId(budget.getUserId());

        budgetRepository.save(existing);

        // Return all budgets for this user (or just List.of(existing) if you want only
        // updated one)
        return budgetRepository.findByUserId(existing.getUserId());
    }

    public List<Budget> getAllBudget() {
        return budgetRepository.findAll();
    }
}
