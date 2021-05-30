package logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingDateFormatter {

    /*
     * Datum wordt op 'dd-mm-jjjj' geformatteerd.
     */
    protected static String getFormattedDate (LocalDateTime date) {
        return date.format (DateTimeFormatter.ofPattern ("dd-MM-yyyy"));
    }

    /*
     * Datum en tijd worden op 'dd-mm-jjjj hh:mm:ss' geformatteerd.
     */
    protected static String getFormattedDateAndTime(LocalDateTime date) {
        return getFormattedDate (date) + date.format (DateTimeFormatter.ofPattern (" hh:mm:ss"));
    }
}