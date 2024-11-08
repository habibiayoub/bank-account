package com.demo.bankaccount.infrastructure.adapter.rest.dto;

import com.demo.bankaccount.domain.transaction.Transaction;
import com.demo.bankaccount.utils.enums.AccountType;

import java.math.BigDecimal;
import java.util.List;

public record AccountStatementResponse(AccountType accountType, BigDecimal balance, List<Transaction> transactions) { }
