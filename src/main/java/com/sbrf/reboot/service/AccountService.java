package com.sbrf.reboot.service;

public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isAccountExist(long id, Account account) {
        //получаем все аккаунты пользователя из источника и проверяем есть ли в их списке предложенный
        return accountRepository.getAllAccountsByClientId(id).contains(account);
    }
}
