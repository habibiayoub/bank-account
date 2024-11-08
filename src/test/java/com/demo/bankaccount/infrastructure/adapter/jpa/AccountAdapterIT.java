package com.demo.bankaccount.infrastructure.adapter.jpa;

import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.infrastructure.adapter.jpa.repository.AccountRepository;
import com.demo.bankaccount.utils.enums.AccountType;
import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AccountAdapterIT {

    private AccountAdapter accountAdapter;
    @Autowired
    private AccountRepository accountRepository;

    private Account accountToSave;

    @BeforeEach
    void setUp() {
        accountAdapter = new AccountAdapter(accountRepository);

        accountToSave = new Account("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(450), new BigDecimal(0), Collections.emptyList());
    }

    @Test
    void findByAccountNumber_success() {
        Account account = accountAdapter.findByAccountNumber("ACCOUNT-0001");

        assertNotNull(account);
        assertEquals("ACCOUNT-0001", account.getAccountNumber());
        assertInstanceOf(Account.class, account);
    }

    @Test
    void findByAccountNumber_failure_accountNotFound() {
        assertThrows(InvalidOperationException.class, () -> accountAdapter.findByAccountNumber("FAKE-ACCOUNT-NUMBER"),
                "Account not found");
    }

    @Test
    void save() {
        accountAdapter.save(accountToSave);

        Account account = accountAdapter.findByAccountNumber("ACT-0001");

        assertNotNull(account);
    }
}