package logging;

import login.AuthenticationSimple;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggingTest {

    @Test
    public void formatTest () {
        AuthenticationSimple.getInstance ().authenticate ("user3", "3");
        String expected = LoggingFormatter.getFormattedDateAndTime() + String.format (" %-20s ", "user3") + "test" + System.lineSeparator ();
        LoggingFormatter formatter = new LoggingFormatter ();
        String actual = new LoggingFormatter ().format (new LogRecord (Level.WARNING, "test"));
        assertEquals (expected, actual);
    }
}