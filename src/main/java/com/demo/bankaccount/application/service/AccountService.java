package com.demo.bankaccount.application.service;

import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.domain.ports.in.AccountUseCase;
import com.demo.bankaccount.domain.ports.out.AccountRepositoryPort;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountStatementResponse;
import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService implements AccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;

    public AccountService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Account creditAccount(String accountNumber, BigDecimal amount) {
        Account account = accountRepositoryPort.findByAccountNumber(accountNumber);
        if (account != null) {
            account.creditAccount(amount);
            accountRepositoryPort.save(account);
        } else {
            throw new InvalidOperationException("Account not found");
        }
        return account;
    }

    @Override
    public Account debitAccount(String accountNumber, BigDecimal amount) {
        Account account = accountRepositoryPort.findByAccountNumber(accountNumber);
        if (account != null) {
            account.debitAccount(amount);
            accountRepositoryPort.save(account);
        } else {
            throw new InvalidOperationException("Account not found");
        }
        return account;
    }

    @Override
    public AccountStatementResponse getAccountStatement(String accountNumber) {
        Account account = accountRepositoryPort.findByAccountNumber(accountNumber);

        if (account != null) {
            return account.getAccountStatement(account);
        } else {
            throw new InvalidOperationException("Account not found");
        }
    }
}
