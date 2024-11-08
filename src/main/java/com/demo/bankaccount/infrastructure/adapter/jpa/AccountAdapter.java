package com.demo.bankaccount.infrastructure.adapter.jpa;

import com.demo.bankaccount.domain.ports.out.AccountRepositoryPort;
import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.domain.transaction.Transaction;
import com.demo.bankaccount.infrastructure.adapter.jpa.entity.AccountEntity;
import com.demo.bankaccount.infrastructure.adapter.jpa.entity.TransactionEntity;
import com.demo.bankaccount.infrastructure.adapter.jpa.repository.AccountRepository;
import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountAdapter implements AccountRepositoryPort {

    private final AccountRepository accountRepository;

    public AccountAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(accountNumber);
        if (accountEntity == null) {
            throw new InvalidOperationException("Account not found");
        }
        return toDomain(accountEntity);
    }

    @Override
    public void save(Account account) {
        AccountEntity accountEntity = toEntity(account);
        accountRepository.save(accountEntity);
    }

    private Account toDomain(AccountEntity accountEntity) {
        return new Account(accountEntity.getAccountNumber(), accountEntity.getType(), accountEntity.getBalance(),
                accountEntity.isOverdraftAuthorised(), accountEntity.getOverdraftAmount(), accountEntity.getDepositeLimit(),
                accountEntity.getTransactions().stream().map(transactionEntity -> new Transaction(transactionEntity.getId(), transactionEntity.getType(), transactionEntity.getAmount(), transactionEntity.getDate())).collect(Collectors.toList()));
    }

    private AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = accountRepository.findByAccountNumber(account.getAccountNumber());
        if (accountEntity == null) {
            return new AccountEntity(account.getAccountNumber(), account.getAccountType(), account.getBalance(),
                    account.isOverdraftAuthorised(), account.getOverdraftAmount(), account.getDepositeLimit(),
                    account.getTransactions().stream().map(transaction -> new TransactionEntity(transaction.id(), transaction.transactionType(), transaction.amount(), transaction.date(), accountEntity)).collect(Collectors.toList()));
        } else {
            accountEntity.setBalance(account.getBalance());
            accountEntity.setTransactions(account.getTransactions().stream().map(transaction -> new TransactionEntity(transaction.id(), transaction.transactionType(), transaction.amount(), transaction.date(), accountEntity)).collect(Collectors.toList()));
            return accountEntity;
        }
    }

}
