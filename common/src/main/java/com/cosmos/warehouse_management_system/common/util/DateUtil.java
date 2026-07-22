package com.cosmos.warehouse_management_system.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private DateUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getCurrentTimestamp() {
        return LocalDateTime.now(ZoneOffset.UTC).format(FORMATTER);
    }

}