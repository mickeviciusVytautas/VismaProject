package auxilary;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss", Locale.US);
        return localDateTime.format(dataTimeFormatter);
    }

}
