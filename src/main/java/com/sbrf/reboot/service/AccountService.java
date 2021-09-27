package com.sbrf.reboot.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public boolean isAccountExist(long id, Account account) throws IOException {
        //получаем все аккаунты пользователя из источника и проверяем есть ли в их списке предложенный
        return accountRepository.getAllAccountsByClientId(id).contains(account);
    }

    @SneakyThrows
    public Account getMaxAccountBalance(long id) {
        Account account = accountRepository.getAllAccountsByClientId(id).stream()
                .max(Comparator.comparing(Account::getBalance))
                .orElse(Account.builder().balance(new BigDecimal(0)).build());
        return account;
    }

    @SneakyThrows
    public Set<Account> getAllAccountsByDateMoreThen(long id, LocalDate filterDate) {
        return accountRepository.getAllAccountsByClientId(id).stream()
                .filter(a ->
                        filterDate.isBefore(a.getCreateDate()) || filterDate.isEqual(a.getCreateDate()))
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    public BigDecimal getTotalBalanceById(long id) {
        return accountRepository.getAllAccountsByClientId(id).stream()
                .map(Account::getBalance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }
}
