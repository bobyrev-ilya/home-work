package com.sbrf.reboot.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;
import lombok.SneakyThrows;

public class XMLUtils {
    private static final XmlMapper xmlmapper = new XmlMapper();

    @SneakyThrows
    public static String toXML(Object o) {
        return xmlmapper.writeValueAsString(o);
    }

    @SneakyThrows
    public static Request XMLtoRequest(String xml) {
        return xmlmapper.readValue(xml, Request.class);
    }

    @SneakyThrows
    public static Response XMLtoResponse(String xml) {
        return xmlmapper.readValue(xml, Response.class);
    }
}
