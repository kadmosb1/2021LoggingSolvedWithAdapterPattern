package logging;

import invoice.Logging;
import login.Authentication;
import login.AuthenticationNormal;
import login.AuthenticationSimple;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggingTest {

    @Test
    public void loggingTest () {

        Authentication login = AuthenticationNormal.getInstance ();
        login.logout ();
        LoggingAdapter logging = LoggingAdapter.getInstance ();

        String actualLogString = logging.getLogString ("test");
        String expectedLogString = "";

        // Als de logfile nog niet bestaat, wordt een header toegevoegd.
        if (!logging.logFileExists()) {
            expectedLogString = String.format ("%-19s %-20s %s%n", "Date", "Gebruikersnaam", "Logging");
        }

        // Omdat er niemand ingelogd is (net logout aangeroepen), wordt voor gebruiker unknown getoond.
        expectedLogString += String.format ("%-19s %-20s %s%n", logging.getFormattedDateAndTime (), "unknown", "test");
        assertEquals (expectedLogString, actualLogString);

        // Als user1 is ingelogd, wordt dat getoond in de log.
        login.authenticate ("user1", "1");
        actualLogString = logging.getLogString ("test");
        expectedLogString = "";

        if (!logging.logFileExists()) {
            expectedLogString = String.format ("%-19s %-20s %s%n", "Date", "Gebruikersnaam", "Logging");
        }

        expectedLogString += String.format ("%-19s %-20s %s%n", logging.getFormattedDateAndTime (), "user1", "test");
        assertEquals (expectedLogString, actualLogString);
    }

    @Test
    public void formatTest () {
        AuthenticationSimple.getInstance ().authenticate ("user3", "3");
        LoggingFormatter formatter = new LoggingFormatter ();
        String expected = formatter.getFormattedDateAndTime() + String.format (" %-20s", "user3") + "test" + System.lineSeparator ();
        String actual = new LoggingFormatter ().format (new LogRecord (Level.WARNING, "test"));
        assertEquals (expected, actual);
    }
}