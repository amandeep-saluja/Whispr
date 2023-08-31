package com.chat.whispr.utils;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utility {

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static String generateId(String prefix) {
        if(null != prefix) {
            String[] ids = UUID.randomUUID().toString().split("-");
            ids[0] = prefix;
            return String.join("-", ids);
        }
        return null;
    }
}
