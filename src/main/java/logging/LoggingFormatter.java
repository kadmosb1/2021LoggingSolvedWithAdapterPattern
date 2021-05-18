package logging;

import login.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LoggingFormatter extends Formatter {

    protected static String getFormattedDate () {
        return LocalDate.now ().format (DateTimeFormatter.ofPattern ("dd-MM-yyyy"));
    }

    protected static String getFormattedDateAndTime() {
        return getFormattedDate () + LocalDateTime.now ().format (DateTimeFormatter.ofPattern (" hh:mm:ss"));
    }

    private String getFileNameForLogging () {
        return "src\\main\\resources\\logging\\" + getFormattedDate () + ".log";
    }

    protected File getFileForLogging () {
        return new File (getFileNameForLogging ());
    }

    @Override
    public String format(LogRecord record) {
        String pre = getFormattedDateAndTime() + " ";
        pre += String.format ("%-20s ", AuthenticationSimple.getInstance ().getUserNameOfActiveUser ());
        return pre + formatMessage (record) + System.lineSeparator ();
    }

    protected boolean logFileDoesNotExist () {
        return !getFileForLogging ().exists ();
    }

    @Override
    public String getHead (Handler handler) {

        // Als de logfile nog niet bestaat moet bovenaan de file een header worden geplaatst.
        if (logFileDoesNotExist()) {
            return String.format("%-19s %-20s %s%n", "Date", "Gebruikersnaam", "Logging");
        }
        else {
            return super.getHead(handler);
        }
    }
}