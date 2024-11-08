package com.demo.bankaccount.infrastructure.adapter.rest.controller;

import com.demo.bankaccount.BankAccountV2Application;
import com.demo.bankaccount.infrastructure.adapter.rest.dto.AccountRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BankAccountV2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders httpHeaders = new HttpHeaders();

    @Test
    void deposit() {
        AccountRequest accountRequest = new AccountRequest("ACCOUNT-0001", new BigDecimal(200));

        HttpEntity<AccountRequest> httpEntity = new HttpEntity<>(accountRequest, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort("/accounts/deposit"),
                HttpMethod.POST, httpEntity, String.class
        );

        assertAll(() -> assertTrue(Objects.requireNonNull(response.getBody()).contains("accountNumber")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("accountType")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("balance")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("transactions"))
        );
    }

    @Test
    void withdraw() {
        AccountRequest accountRequest = new AccountRequest("ACCOUNT-0001", new BigDecimal(200));

        HttpEntity<AccountRequest> httpEntity = new HttpEntity<>(accountRequest, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort("/accounts/withdraw"),
                HttpMethod.POST, httpEntity, String.class
        );

        assertAll(() -> assertTrue(Objects.requireNonNull(response.getBody()).contains("accountNumber")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("accountType")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("balance")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("transactions"))
        );
    }

    @Test
    void getStatement() {
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                createUrlWithPort("/accounts/statement/ACCOUNT-0001"),
                HttpMethod.GET, httpEntity, String.class
        );

        assertAll(() -> assertTrue(Objects.requireNonNull(response.getBody()).contains("accountType")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("balance")),
                () -> assertTrue(Objects.requireNonNull(response.getBody()).contains("transactions"))
        );
    }

    private String createUrlWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}