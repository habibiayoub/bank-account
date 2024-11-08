package com.demo.bankaccount.infrastructure.adapter.jpa.entity;

import com.demo.bankaccount.utils.enums.AccountType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "is_overdraft_authorised")
    private boolean isOverdraftAuthorised;

    @Column(name = "overdraft_amount")
    private BigDecimal overdraftAmount;

    @Column(name = "deposite_limit")
    private BigDecimal depositeLimit;

    @OneToMany(mappedBy = "accountEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    public AccountEntity() {
    }

    public AccountEntity(String accountNumber, AccountType type, BigDecimal balance, boolean isOverdraftAuthorised, BigDecimal overdraftAmount, BigDecimal depositeLimit, List<TransactionEntity> transactions) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = balance;
        this.isOverdraftAuthorised = isOverdraftAuthorised;
        this.overdraftAmount = overdraftAmount;
        this.depositeLimit = depositeLimit;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isOverdraftAuthorised() {
        return isOverdraftAuthorised;
    }

    public void setOverdraftAuthorised(boolean overdraftAuthorised) {
        isOverdraftAuthorised = overdraftAuthorised;
    }

    public BigDecimal getOverdraftAmount() {
        return overdraftAmount;
    }

    public void setOverdraftAmount(BigDecimal overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public BigDecimal getDepositeLimit() {
        return depositeLimit;
    }

    public void setDepositeLimit(BigDecimal depositeLimit) {
        this.depositeLimit = depositeLimit;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
