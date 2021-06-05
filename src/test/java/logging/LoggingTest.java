package logging;

import login.AuthenticationSimple;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggingTest {

    @Test
    public void formatTest () {
        LocalDateTime now = LocalDateTime.now ();

        Logger logger = Logger.getLogger (LoggingAdapter.class.getName ());
        LoggingFileHandler fileHandler = new LoggingFileHandler (logger);
        AuthenticationSimple.getInstance ().authenticate ("user3", "3");
        String expected = LoggingDateFormatter.getFormattedDateAndTime (now) + String.format (" %-20s ", "user3") + "test" + System.lineSeparator ();
        LoggingFormatter formatter = new LoggingFormatter (fileHandler);
        String actual = new LoggingFormatter (fileHandler).format (new LogRecord (Level.WARNING, "test"), now);
        assertEquals (expected, actual);
    }
}