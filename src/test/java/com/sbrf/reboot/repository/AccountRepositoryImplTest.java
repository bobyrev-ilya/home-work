package com.sbrf.reboot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryImplTest {

    AccountRepository accountRepository;


    @Test
    void onlyPersonalAccounts() throws IOException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<String> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1);
        ArrayList<String> strings = new ArrayList<String>() {{
            add("2-ACCNUM");
            add("1-ACCNUM");
            add("4-ACC1NUM");
        }};

        assertTrue(strings.containsAll(allAccountsByClientId));
    }

    @Test
    void successGetAllAccountsByClientId() throws IOException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<String> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1);
        Assertions.assertTrue(allAccountsByClientId.contains("5-ACC1NUM"));
    }

    @Test
    void failGetAllAccountsByClientId() {
        accountRepository = new AccountRepositoryImpl("somePath");
        assertThrows(FileNotFoundException.class, () -> {
            accountRepository.getAllAccountsByClientId(1L);
        });
    }


}