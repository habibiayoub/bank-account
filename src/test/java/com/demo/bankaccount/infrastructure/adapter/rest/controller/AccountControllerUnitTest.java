package com.demo.bankaccount.infrastructure.adapter.rest.controller;

import com.demo.bankaccount.domain.account.Account;
import com.demo.bankaccount.domain.ports.in.AccountUseCase;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountStatementResponse;
import com.demo.bankaccount.utils.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountUseCase  accountUseCase;

    @Test
    void deposit() throws Exception {
        Account account = new Account("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(450), new BigDecimal(0), Collections.emptyList());

        when(accountUseCase.creditAccount(Mockito.anyString(), Mockito.any(BigDecimal.class))).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountNumber\":\"ACT-0001\",\"amount\":200.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("450"));
    }

    @Test
    void withdraw() throws Exception {
        Account account = new Account("ACT-0001", AccountType.CHECKING_ACCOUNT, new BigDecimal(450),
                true, new BigDecimal(450), new BigDecimal(0), Collections.emptyList());

        when(accountUseCase.debitAccount(Mockito.anyString(), Mockito.any(BigDecimal.class))).thenReturn(account);

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"ACT-0001\",\"amount\":200.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("450"));
    }

    @Test
    void getStatement() throws Exception {
        AccountStatementResponse accountStatementResponse = new AccountStatementResponse(AccountType.CHECKING_ACCOUNT,
                new BigDecimal(450), Collections.emptyList());

        when(accountUseCase.getAccountStatement(Mockito.anyString())).thenReturn(accountStatementResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/statement/ACCOUNT-0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("450"))
                .andExpect(jsonPath("$.transactions").isEmpty());
    }
}