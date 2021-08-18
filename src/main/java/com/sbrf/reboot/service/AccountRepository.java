package com.sbrf.reboot.service;

import java.util.Set;

interface AccountRepository {
    Set<Account> getAllAccountsByClientId(long id);
}
