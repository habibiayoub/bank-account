package com.demo.bankaccount.domain.account;

import com.demo.bankaccount.domain.transaction.Transaction;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountStatementResponse;
import com.demo.bankaccount.utils.enums.AccountType;
import com.demo.bankaccount.utils.enums.TransactionType;
import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountUnitTest {

    private Account checkingAccount;
    private Account savingAccount;
    private List<Transaction> transactions;

    @BeforeEach
    void setUp() {
        checkingAccount = new Account("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(200), new BigDecimal(0), new ArrayList<>());

        savingAccount = new Account("ACT-0002", AccountType.SAVINGS_ACCOUNT, new BigDecimal(2000),
                false, new BigDecimal(0), new BigDecimal(10000), new ArrayList<>());

        transactions = Arrays.asList(
                new Transaction(1L, TransactionType.CREDIT, new BigDecimal("100.00"), LocalDateTime.now().minusDays(5)),
                new Transaction(2L, TransactionType.DEBIT, new BigDecimal("50.00"), LocalDateTime.now().minusDays(10)),
                new Transaction(3L, TransactionType.CREDIT, new BigDecimal("200.00"), LocalDateTime.now().minusDays(35))
        );
    }

    @Test
    void creditCheckingAccount() {
        Account result = checkingAccount.creditAccount(new BigDecimal(250));

        assertEquals(new BigDecimal("700"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.CREDIT, result.getTransactions().get(0).transactionType());
        assertEquals(new BigDecimal(250), result.getTransactions().get(0).amount());
    }

    @Test
    void creditSavingAccount() {
        Account result = savingAccount.creditAccount(new BigDecimal(1000));

        assertEquals(new BigDecimal("3000"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.CREDIT, result.getTransactions().get(0).transactionType());
        assertEquals(new BigDecimal(1000), result.getTransactions().get(0).amount());
    }

    @Test
    void creditSavingAccountExceedLimit() {
        assertThrows(InvalidOperationException.class, () -> savingAccount.creditAccount(new BigDecimal(10000)),
                "Cannot credit savings account: Limit threshold reached");
    }

    @Test
    void debitCheckingAccount() {
        Account result = checkingAccount.debitAccount(new BigDecimal(250));

        assertEquals(new BigDecimal("200"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.DEBIT, result.getTransactions().get(0).transactionType());
        assertEquals(new BigDecimal(250), result.getTransactions().get(0).amount());
    }

    @Test
    void debitCheckingAccountWithinOverdraftLimit() {
        Account result = checkingAccount.debitAccount(new BigDecimal(500));

        assertEquals(new BigDecimal("-50"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.DEBIT, result.getTransactions().get(0).transactionType());
        assertEquals(new BigDecimal(500), result.getTransactions().get(0).amount());
    }

    @Test
    void debitCheckingAccountExceedOverdraftLimit() {
        assertThrows(InvalidOperationException.class, () -> checkingAccount.debitAccount(new BigDecimal(1000)),
                "Cannot debit checking account: Insufficient balance");
    }

    @Test
    void debitSavingAccount() {
        Account result = savingAccount.debitAccount(new BigDecimal(1000));

        assertEquals(new BigDecimal("1000"), result.getBalance());
        assertEquals(1, result.getTransactions().size());
        assertEquals(TransactionType.DEBIT, result.getTransactions().get(0).transactionType());
        assertEquals(new BigDecimal(1000), result.getTransactions().get(0).amount());
    }

    @Test
    void debitSavingAccountWithInsufficientBalance() {
        assertThrows(InvalidOperationException.class, () -> savingAccount.debitAccount(new BigDecimal(4000)),
                "Cannot debit savings account: Insufficient balance");
    }

    @Test
    void getAccountStatement() {
        Account account = new Account();
        account.setTransactions(transactions);

        AccountStatementResponse response = account.getAccountStatement(account);

        assertEquals(2, response.transactions().size());
        assertEquals(1L, response.transactions().get(0).id());
        assertEquals(2L, response.transactions().get(1).id());
    }
}