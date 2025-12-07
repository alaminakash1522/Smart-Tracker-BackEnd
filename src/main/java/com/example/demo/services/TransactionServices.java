package com.example.demo.services;

import com.example.demo.model.Transaction;
import com.example.demo.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServices {
    @Autowired
    TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUser(Long userId) {
        return transactionRepository.findByUserId(userId);
    }


    public List<Transaction> getIncome(Long userId) {
        return transactionRepository.findByUserIdAndTransactionTypeTrue(userId);

    }

    public List<Transaction> getExpense(Long userId) {
        return transactionRepository.findByUserIdAndTransactionTypeFalse(userId);

    }

    public ResponseEntity<Void> deleteById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
        return ResponseEntity.noContent().build();
    }

    public Transaction updateTransaction(Transaction transaction) {

        // 1. Ensure an ID is provided
        if (transaction.getId() == null) {
            throw new RuntimeException("Transaction ID is required for update");
        }

        // 2. Fetch existing record
        Transaction existing = transactionRepository.findById(transaction.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Transaction not found with ID: " + transaction.getId()
                ));

        // 3. Update relevant fields
        existing.setAmount(transaction.getAmount());
        existing.setSource(transaction.getSource());
        existing.setTransactionAt(transaction.getTransactionAt());
        existing.setTransactionType(transaction.gettransactionType());
        existing.setDescription(transaction.getDescription());
        existing.setUserId(transaction.getUserId()); // If user cannot change, remove this

        // 4. Save and return updated entity
        return transactionRepository.save(existing);
    }
}
