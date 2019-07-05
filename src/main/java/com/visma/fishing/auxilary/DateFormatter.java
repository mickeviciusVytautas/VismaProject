package com.visma.fishing.auxilary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss", Locale.US);
        return localDateTime.format(dataTimeFormatter);
    }

}
