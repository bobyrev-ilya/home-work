package com.sbrf.reboot.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;

    public boolean isAccountExist(long id, Account account) {
        //получаем все аккаунты пользователя из источника и проверяем есть ли в их списке предложенный
        return accountRepository.getAllAccountsByClientId(id).contains(account);
    }
}
