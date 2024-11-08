package com.demo.bankaccount.domain.account;

import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountStatementResponse;
import com.demo.bankaccount.domain.transaction.Transaction;
import com.demo.bankaccount.utils.enums.AccountType;
import com.demo.bankaccount.utils.enums.TransactionType;
import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Account {

    /**
     * Un numéro de compte unique
     */
    private String accountNumber;

    /**
     * Type du compte : compte courant ou compte d'épargne
     */
    private AccountType accountType;

    /**
     * Solde du compte
     */
    private BigDecimal balance;

    /**
     * Indique si le découvert et autorisé ou non
     * NB : Possible seulement pour les comptes courants
     */
    private boolean isOverdraftAuthorised;

    /**
     * Montant du découvert autorisé
     */
    private BigDecimal overdraftAmount;

    /**
     * Solde maximum du compte
     * NB : Possible seulement pour les comptes d'épargnes
     */
    private BigDecimal depositeLimit;

    /**
     * Liste des transactions effectuées sur le compte
     */
    private List<Transaction> transactions;

    public Account() {
    }

    public Account(String accountNumber, AccountType accountType, BigDecimal balance, boolean isOverdraftAuthorised, BigDecimal overdraftAmount, BigDecimal depositeLimit, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.isOverdraftAuthorised = isOverdraftAuthorised;
        this.overdraftAmount = overdraftAmount;
        this.depositeLimit = depositeLimit;
        this.transactions = transactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public boolean isOverdraftAuthorised() {
        return isOverdraftAuthorised;
    }

    public BigDecimal getOverdraftAmount() {
        return overdraftAmount;
    }

    public BigDecimal getDepositeLimit() {
        return depositeLimit;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean canCreditAccount(@NotNull BigDecimal newBalance) {
        return depositeLimit.compareTo(newBalance) > 0;
    }

    public boolean canDebitAccount(@NotNull BigDecimal amount) {
        if (!isOverdraftAuthorised()) {
            return false;
        } else {
            return balance.add(overdraftAmount).compareTo(amount) > 0;
        }
    }

    public Account creditAccount(@NotNull BigDecimal amount) {
        if (accountType == AccountType.CHECKING_ACCOUNT || canCreditAccount(balance.add(amount))) {
            balance = balance.add(amount);
            this.transactions.add(new Transaction(null, TransactionType.CREDIT, amount, LocalDateTime.now()));
        } else {
            throw new InvalidOperationException("Cannot credit savings account: Limit threshold reached");
        }

        return this;
    }

    public Account debitAccount(@NotNull BigDecimal amount) {
        if (amount.compareTo(balance) < 0 || (accountType != AccountType.SAVINGS_ACCOUNT && canDebitAccount(amount))) {
            balance = balance.subtract(amount);
            this.transactions.add(new Transaction(null, TransactionType.DEBIT, amount, LocalDateTime.now()));
        } else if (accountType == AccountType.SAVINGS_ACCOUNT) {
            throw new InvalidOperationException("Cannot debit savings account: Insufficient balance");
        } else {
            throw new InvalidOperationException("Cannot debit checking account: Insufficient balance");
        }

        return this;
    }

    public AccountStatementResponse getAccountStatement(Account account) {
        LocalDateTime oneMouthAgo = LocalDateTime.now().minusMonths(1);

        return new AccountStatementResponse(account.getAccountType(),
                account.getBalance(),
                account.getTransactions().stream()
                        .filter(transaction -> transaction.date().isAfter(oneMouthAgo))
                        .sorted((t1, t2) -> t2.date().compareTo(t1.date()))
                        .toList());
    }

}
