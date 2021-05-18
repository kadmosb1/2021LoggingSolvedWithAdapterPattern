package logging;

import login.Authentication;
import login.AuthenticationNormal;
import org.junit.jupiter.api.Test;

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
}