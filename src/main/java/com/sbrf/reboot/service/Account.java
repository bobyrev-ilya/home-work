package com.sbrf.reboot.service;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@Data
@EqualsAndHashCode(of = {"number"})
public class Account {
    private String number; //номер аккаунта
    private long id;
    private LocalDate createDate;
    private BigDecimal balance;
}
