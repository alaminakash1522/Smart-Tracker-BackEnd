package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    TransactionServices transactionServices;

    @GetMapping("/")
    public String index() {
        return "Checking Transactions Endpoint!";
    }

    @PostMapping("/addTransaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionServices.createTransaction(transaction);
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable Long userId) {
        return transactionServices.getTransactionsByUser(userId);
    }

    @GetMapping("/getIncome/{userId}")
    public List<Transaction> getIncome(@PathVariable Long userId) {
        return transactionServices.getIncome(userId);
    }

    
    @GetMapping("/getExpense/{userId}")
    public List<Transaction> getExpense(@PathVariable Long userId) {
        return transactionServices.getExpense(userId);
    }

    @DeleteMapping("/deleteTransaction/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionServices.deleteById(transactionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateTransaction")
    public Transaction updateTransaction(@RequestBody Transaction transaction){
        return transactionServices.updateTransaction(transaction);
    }

    // @GetMapping("/category/{categoryId}")
    // public List<Transaction> getTransactionsByCategory(@PathVariable Long
    // categoryId) {
    // return transactionServices.getTransactionsByCategory(categoryId);
    // }
}
