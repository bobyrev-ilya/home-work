package com.sbrf.reboot.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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

        assertFalse(accountService.isAccountExist(1L, Account.builder().number("ACC1235NUM").build()));
    }

    @SneakyThrows
    @Test
    void getMaxAccountBalance() {
        Account accountWithMaxBalance = Account.builder().number("4").clientId(1L).id(4L).balance(new BigDecimal(150000)).build();
        Set<Account> accounts = new HashSet() {{
            add(Account.builder().number("1").clientId(1L).id(1L).balance(BigDecimal.TEN).build());
            add(Account.builder().number("2").clientId(1L).id(2L).balance(new BigDecimal(200)).build());
            add(Account.builder().number("3").clientId(1L).id(3L).balance(new BigDecimal("1.65")).build());
            add(accountWithMaxBalance);
        }};

        when(accountRepository.getAllAccountsByClientId(1L)).thenReturn(accounts);

        assertEquals(accountWithMaxBalance, accountService.getMaxAccountBalance(1L));
    }


    @SneakyThrows
    @Test
    void getAllAccountsByDateMoreThen() {
        Account account1 = Account.builder().clientId(1L)
                .createDate(LocalDate.now().minusDays(2))
                .number("1").build();
        Account account2 = Account.builder().clientId(1L)
                .createDate(LocalDate.now().minusDays(3))
                .number("2").build();
        Account account3 = Account.builder().clientId(1L)
                .createDate(LocalDate.now().minusDays(1))
                .number("3").build();
        Account account4 = Account.builder().clientId(1L)
                .createDate(LocalDate.now().minusDays(7))
                .number("4").build();

        Set<Account> accounts = new HashSet() {{
            add(account1);
            add(account2);
            add(account3);
            add(account4);
        }};

        when(accountRepository.getAllAccountsByClientId(1L)).thenReturn(accounts);

        Set allAccountsByDateMoreThen = accountService.getAllAccountsByDateMoreThen(1L, LocalDate.now().minusDays(2));

        assertEquals(2, allAccountsByDateMoreThen.size());
        assertTrue(allAccountsByDateMoreThen.contains(account3));
    }

    @SneakyThrows
    @Test
    void getTotalBalanceById() {
        Set<Account> accounts = new HashSet() {
            {
                add(Account.builder().number("1").clientId(1L).id(1L).balance(BigDecimal.TEN).build());
                add(Account.builder().number("2").clientId(1L).id(2L).balance(new BigDecimal(200)).build());
                add(Account.builder().number("3").clientId(1L).id(3L).balance(new BigDecimal("1.65")).build());
                add(Account.builder().number("4").clientId(1L).id(3L).balance(new BigDecimal("15")).build());
                add(Account.builder().number("5").clientId(1L).id(3L).balance(new BigDecimal("44")).build());
            }
        };
        when(accountRepository.getAllAccountsByClientId(1L)).thenReturn(accounts);
        assertEquals(BigDecimal.valueOf(270.65), accountService.getTotalBalanceById(1L));
    }
}