package com.sbrf.reboot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;
import lombok.SneakyThrows;

public class JSONUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static String toJSON(Object o) {
        return objectMapper.writeValueAsString(o);
    }

    @SneakyThrows
    public static Request JSONtoRequest(String json) {
        return objectMapper.readValue(json, Request.class);
    }

    @SneakyThrows
    public static Response JSONtoResponse(String json) {
        return objectMapper.readValue(json, Response.class);
    }
}
