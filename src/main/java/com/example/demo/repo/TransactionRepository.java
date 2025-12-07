package com.example.demo.repo;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByUserIdAndTransactionTypeTrue(Long userId);
    List<Transaction> findByUserIdAndTransactionTypeFalse(Long userId);
    void deleteById(long transactionId);
    // List<Transaction> findByCategoryId(Long categoryId);
}
