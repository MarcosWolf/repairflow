package com.marcoswolf.repairflow.ui.utils;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isEmpty(Object value) {
        return value == null;
    }

    public static boolean isEmpty(Long value) {
        return value == null || value == 0;
    }
}
