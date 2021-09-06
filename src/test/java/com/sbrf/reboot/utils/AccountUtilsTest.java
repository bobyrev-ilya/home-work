package com.sbrf.reboot.utils;

import com.sbrf.reboot.service.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class AccountUtilsTest {

    @Test
    void sortedById() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id(3L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(1L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(2L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedById(accounts);

        Assertions.assertEquals(1L, accounts.get(0).getId());
        Assertions.assertEquals(2L, accounts.get(1).getId());
        Assertions.assertEquals(3L, accounts.get(2).getId());
        Assertions.assertEquals(3L, accounts.get(3).getId());

    }

    @Test
    void sortedByIdDate() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id(1L).createDate(LocalDate.now().minusDays(4)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now().minusDays(3)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now().minusDays(1)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(2L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedByIdDate(accounts);

        Assertions.assertEquals(1L, accounts.get(0).getId());
        Assertions.assertEquals(2L, accounts.get(1).getId());
        Assertions.assertEquals(LocalDate.now().minusDays(3), accounts.get(2).getCreateDate());
        Assertions.assertEquals(LocalDate.now().minusDays(1), accounts.get(3).getCreateDate());
    }

    @Test
    void sortedByIdDateBalance() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id(1L).createDate(LocalDate.now().minusDays(4)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(2L).createDate(LocalDate.now().minusDays(1)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(4L).createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now().minusDays(2)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(3L).createDate(LocalDate.now().minusDays(1)).balance(BigDecimal.TEN).build());
            add(Account.builder().id(4L).createDate(LocalDate.now()).balance(BigDecimal.ONE).build());
        }};

        AccountUtils.sortedByIdDateBalance(accounts);

        Assertions.assertEquals(1L, accounts.get(0).getId());
        Assertions.assertEquals(2L, accounts.get(1).getId());
        Assertions.assertEquals(LocalDate.now().minusDays(2), accounts.get(2).getCreateDate());
        Assertions.assertEquals(LocalDate.now().minusDays(1), accounts.get(3).getCreateDate());
        Assertions.assertEquals(BigDecimal.ONE, accounts.get(4).getBalance());
        Assertions.assertEquals(BigDecimal.TEN, accounts.get(5).getBalance());
    }
}