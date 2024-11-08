package com.demo.bankaccount.infrastructure.adapter.rest.controller;

import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountRequest;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountStatementResponse;
import com.demo.bankaccount.domain.ports.in.AccountUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountUseCase accountUseCase;

    public AccountController(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountUseCase.creditAccount(request.getAccountNumber(), request.getAmount()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountUseCase.debitAccount(request.getAccountNumber(), request.getAmount()));
    }

    @GetMapping("/statement/{accountNumber}")
    public ResponseEntity<AccountStatementResponse> getStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountUseCase.getAccountStatement(accountNumber));
    }

}
