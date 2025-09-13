package jung.util;

import java.time.format.DateTimeFormatter;

/**
 * Centralized date formatting patterns for consistent date handling across the application.
 */
public class DateFormats {
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy, h:mm a");

    private DateFormats() {
    }
}
