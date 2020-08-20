package com.api.error.center.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public abstract class LogEventTest {

    protected final LocalDateTime startDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
    protected static LocalDateTime endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(0);
    protected static Pageable pageable = PageRequest.of(0, 10);

    protected static final String DESCRIPTION_A = "Description A";
    protected static final String DESCRIPTION_B = "Description B";
    protected static final String DESCRIPTION_C = "Description C";

    protected static final String LOG_A = "Log A";
    protected static final String LOG_B = "Log B";
    protected static final String LOG_C = "Log C";

    protected static final LocalDateTime DATE_A = LocalDateTime.of(2020, 8, 5, 15, 30, 50);
    protected static final LocalDateTime DATE_B = LocalDateTime.of(2020, 6, 25, 9, 50, 15);
    protected static final LocalDateTime DATE_C = LocalDateTime.of(2020, 8, 5, 17, 35, 40);
    protected static final LocalDateTime DATE_D = LocalDateTime.of(2020, 5, 3, 15, 30, 50);
}
