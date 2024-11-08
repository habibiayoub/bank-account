package com.demo.bankaccount.domain.ports.in;

import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountStatementResponse;

import java.math.BigDecimal;

public interface AccountUseCase {
    Account creditAccount(String accountNumber, BigDecimal amount);
    Account debitAccount(String accountNumber, BigDecimal amount);
    AccountStatementResponse getAccountStatement(String accountNumber);
}
