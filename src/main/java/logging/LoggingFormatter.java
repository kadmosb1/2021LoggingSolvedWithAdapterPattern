package logging;

import login.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggingFormatter extends Formatter {

    protected String getFormattedDate () {
        return LocalDate.now ().format (DateTimeFormatter.ofPattern ("dd-MM-yyyy"));
    }

    protected String getFormattedDateAndTime() {
        return getFormattedDate () + LocalDateTime.now ().format (DateTimeFormatter.ofPattern (" hh:mm:ss"));
    }

    @Override
    public String format(LogRecord record) {
        String pre = getFormattedDateAndTime() + " ";
        pre += String.format ("%-20s", AuthenticationSimple.getInstance ().getUserNameOfActiveUser ());
        return pre + formatMessage (record) + System.lineSeparator ();
    }
}