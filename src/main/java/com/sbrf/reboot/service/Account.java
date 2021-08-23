package com.sbrf.reboot.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@EqualsAndHashCode(of = {"number"})
public class Account {
    @Getter
    private String number; //номер аккаунта

}
