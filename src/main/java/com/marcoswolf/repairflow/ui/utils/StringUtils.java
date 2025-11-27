package com.marcoswolf.repairflow.ui.utils;

public class StringUtils {
    private StringUtils() {
    }

    public static String trimOrNull(String value) {
        return value == null ? null : value.trim();
    }

    public static String toUpperOrNull(String value) {
        return value == null ? null : value.trim().toUpperCase();
    }

    public static String toLowerOrNull(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
