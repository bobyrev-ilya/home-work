package com.sbrf.reboot.atm.cassettes;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class Cassette<Banknote> {

    ArrayList<Banknote> banknotes;

    public int getCountBanknotes() {
        return banknotes.size();
    }
}
