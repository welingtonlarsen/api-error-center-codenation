package com.api.error.center.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDateTime convertStringToLocalDateTime(String date) {
        if (date == null) return null;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public static boolean validateDateWithRegex(String date) {
        return date.matches("^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");
    }


}
