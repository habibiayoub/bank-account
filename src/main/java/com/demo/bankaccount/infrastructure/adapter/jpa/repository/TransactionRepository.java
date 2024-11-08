package com.demo.bankaccount.infrastructure.adapter.jpa.repository;

import com.demo.bankaccount.infrastructure.adapter.jpa.entity.AccountEntity;
import com.demo.bankaccount.infrastructure.adapter.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findTransactionsByAccountEntity(AccountEntity accountEntity);
}
