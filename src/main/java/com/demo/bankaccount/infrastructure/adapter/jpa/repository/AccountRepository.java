package com.demo.bankaccount.infrastructure.adapter.jpa.repository;

import com.demo.bankaccount.infrastructure.adapter.jpa.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByAccountNumber(String accountNumber);
}
