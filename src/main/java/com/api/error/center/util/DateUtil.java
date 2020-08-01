package com.api.error.center.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDateTime convertStringToLocalDateTime(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

}
