package com.demo.bankaccount.infrastructure.adapter.jpa;

import com.demo.bankaccount.domain.ports.out.TransactionRepositoryPort;
import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.domain.transaction.Transaction;
import com.demo.bankaccount.infrastructure.adapter.jpa.entity.TransactionEntity;
import com.demo.bankaccount.infrastructure.adapter.jpa.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionAdapter implements TransactionRepositoryPort {

    private final TransactionRepository transactionRepository;

    public TransactionAdapter(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void save(Transaction transaction) {
        TransactionEntity transactionEntity = toEntity(transaction);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public List<Transaction> findTransactionsByAccount(Account account) {
        //List<TransactionEntity> transactionEntities = transactionRepository.findTransactionsByAccountEntity()
        return List.of();
    }

    private Transaction toDomain(TransactionEntity transactionEntity) {
        return new Transaction(transactionEntity.getId(), transactionEntity.getType(), transactionEntity.getAmount(),
                transactionEntity.getDate());
    }

    private TransactionEntity toEntity(Transaction transaction) {
        //new TransactionEntity(transaction.id(), transaction.transactionType(), transaction.amount(), transaction.date());
        return null;
    }
}
