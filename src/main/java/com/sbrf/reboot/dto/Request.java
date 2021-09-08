package com.sbrf.reboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {
    private String atmNumber;

    public Request() {
    }
}
