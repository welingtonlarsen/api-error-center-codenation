package com.api.error.center.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public class LogEventUtil {

    public static final LocalDateTime startDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    public static LocalDateTime endDate = LocalDateTime.now();
    public static Pageable pageable = PageRequest.of(0, 10);

    public static final String DESCRIPTION_A = "Description A";
    public static final String DESCRIPTION_B = "Description B";
    public static final String DESCRIPTION_C = "Description C";

    public static final String LOG_A = "Log A";
    public static final String LOG_B = "Log B";
    public static final String LOG_C = "Log C";

    public static final LocalDateTime DATE_A = LocalDateTime.of(2020, 8, 5, 15, 30, 50);
    public static final LocalDateTime DATE_B = LocalDateTime.of(2020, 6, 25, 9, 50, 15);
    public static final LocalDateTime DATE_C = LocalDateTime.of(2020, 8, 5, 17, 35, 40);
    public static final LocalDateTime DATE_D = LocalDateTime.of(2020, 5, 3, 15, 30, 50);
}
