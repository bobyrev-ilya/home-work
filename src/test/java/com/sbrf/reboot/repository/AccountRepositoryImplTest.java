package com.sbrf.reboot.repository;

import com.sbrf.reboot.service.Account;
import com.sbrf.reboot.service.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryImplTest {

    AccountRepository accountRepository;


    @Test
    void onlyPersonalAccounts() {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<Account> allAccountsByClientId = new HashSet<>();
        try {
            allAccountsByClientId = accountRepository.getAllAccountsByClientId(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Account> accounts = new HashSet() {{
            add(Account.builder().number("2-ACCNUM").build());
            add(Account.builder().number("1-ACCNUM").build());
            add(Account.builder().number("4-ACC1NUM").build());
        }};

        assertTrue(accounts.containsAll(allAccountsByClientId));
    }

    @Test
    void successGetAllAccountsByClientId() {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<Account> allAccountsByClientId = new HashSet<>();
        try {
            allAccountsByClientId = accountRepository.getAllAccountsByClientId(2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(allAccountsByClientId.contains(Account.builder().number("5-ACC1NUM").build()));
    }

    @Test
    void failGetAllAccountsByClientId() {
        accountRepository = new AccountRepositoryImpl("somePath");
        assertThrows(NoSuchFileException.class, () -> {
            accountRepository.getAllAccountsByClientId(1L);
        });
    }
}