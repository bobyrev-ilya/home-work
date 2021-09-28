package com.sbrf.reboot.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public class Customer {
    private int age;
    private String name;
    private List<Account> accounts;

    public Stream<Account> getAccounts() {
        return accounts.stream();
    }
}
