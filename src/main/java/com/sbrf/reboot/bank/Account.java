package com.sbrf.reboot.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Account {
    private BigDecimal amount;
    private String currency;
    private LocalDate createDate;
}
