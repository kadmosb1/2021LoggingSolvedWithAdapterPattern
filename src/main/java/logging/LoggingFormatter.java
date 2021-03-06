package logging;

import login.*;

import java.time.LocalDateTime;
import java.util.logging.*;

public class LoggingFormatter extends Formatter {

    private final LoggingFileHandler fileHandler;

    /*
     * Om te kunnen controleren of een file bij een fileHandler
     * leeg is, moet deze fileHandler bekend zijn (in de methode getHead).
     */
    public LoggingFormatter (LoggingFileHandler fileHandler) {
        super ();
        this.fileHandler = fileHandler;
    }

    /*
     * Deze methode is toegevoegd om de overridden methode format te kunnen testen.
     */
    protected String format (LogRecord record, LocalDateTime currentTime) {
        String pre = LoggingDateFormatter.getFormattedDateAndTime (currentTime) + " ";
        pre += String.format ("%-20s ", AuthenticationSimple.getInstance ().getUserNameOfActiveUser ());
        return pre + formatMessage (record) + System.lineSeparator ();
    }

    @Override
    public String format(LogRecord record) {
        return format (record, LocalDateTime.now ());
    }

    /*
     * Als de file nog niet bestaat of nog leeg is, wordt
     * een header bovenaan de file getoond.
     */
    @Override
    public String getHead (Handler handler) {

        // Als de logfile nog niet bestaat moet bovenaan de file een header worden geplaatst.
        if (fileHandler.logFileIsEmpty ()) {
            return String.format("%-19s %-20s %s%n", "Date", "Gebruikersnaam", "Logging");
        }
        else {
            return super.getHead(handler);
        }
    }
}