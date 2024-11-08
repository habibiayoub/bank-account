package com.demo.bankaccount.domain.transaction;

import com.demo.bankaccount.utils.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(Long id, TransactionType transactionType, BigDecimal amount, LocalDateTime date) {

}
