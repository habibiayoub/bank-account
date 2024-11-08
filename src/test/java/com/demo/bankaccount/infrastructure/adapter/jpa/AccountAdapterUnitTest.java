package com.demo.bankaccount.infrastructure.adapter.jpa;

import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.infrastructure.adapter.jpa.entity.AccountEntity;
import com.demo.bankaccount.infrastructure.adapter.jpa.repository.AccountRepository;
import com.demo.bankaccount.utils.enums.AccountType;
import com.demo.bankaccount.utils.exceptions.InvalidOperationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountAdapterUnitTest {

    @InjectMocks
    private AccountAdapter accountAdapter;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void findByAccountNumber_success() {
        AccountEntity accountEntity = new AccountEntity("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(450), new BigDecimal(0), Collections.emptyList());

        when(accountRepository.findByAccountNumber(Mockito.any(String.class))).thenReturn(accountEntity);

        Account account = accountAdapter.findByAccountNumber("ACT-0001");

        assertNotNull(account);
        assertEquals("ACT-0001", account.getAccountNumber());
        assertInstanceOf(Account.class, account);
    }

    @Test
    void findByAccountNumber_failure_accountNotFound() {
        when(accountRepository.findByAccountNumber(Mockito.argThat(accountNumber -> accountNumber.equals("FAKE-ACCOUNT-NUMBER")))).thenReturn(null);

        assertThrows(InvalidOperationException.class, () -> accountAdapter.findByAccountNumber("FAKE-ACCOUNT-NUMBER"),
                "Account not found");
    }

    @Test
    void save() {
        AccountEntity accountEntity = new AccountEntity("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(450), new BigDecimal(0), Collections.emptyList());

        Account account = new Account("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(450), new BigDecimal(0), Collections.emptyList());

        when(accountRepository.save(Mockito.any(AccountEntity.class))).thenReturn(accountEntity);

        accountAdapter.save(account);

        when(accountRepository.findByAccountNumber(Mockito.any(String.class))).thenReturn(accountEntity);

        Account accountSaved = accountAdapter.findByAccountNumber("ACT-0001");

        assertNotNull(accountSaved);
    }
}