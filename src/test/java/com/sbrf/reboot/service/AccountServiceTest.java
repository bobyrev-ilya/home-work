package com.sbrf.reboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        //какой-то источник аккаунтов
        accountRepository = Mockito.mock(AccountRepository.class);

        //обработчик источника аккаунтов
        accountService = new AccountService(accountRepository);
    }

    @Test
    void bookExist() throws IOException {
        Account account = Account.builder().number("ACC1234NUM").build();
        Set<Account> accounts = new HashSet();
        accounts.add(account);

        //заглушка: возвращаем ранее созданный Set аккаунтов
        when(accountRepository.getAllAccountsByClientId(1L)).thenReturn(accounts);

        assertTrue(accountService.isAccountExist(1L, account));
    }

    @Test
    void bookNotExist() throws IOException {
        Set<Account> accounts = new HashSet();
        accounts.add(Account.builder().number("ACC1234NUM").build());

        when(accountRepository.getAllAccountsByClientId(1L)).thenReturn(accounts);

        assertFalse(accountService.isAccountExist(1L, Account.builder().number("ACC1234NUM").build()));
    }
}