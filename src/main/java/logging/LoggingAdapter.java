package logging;

import login.AuthenticationSimple;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static logging.LoggingFormatter.getFormattedDate;

public class LoggingAdapter {

    private static final Logger LOGGER = Logger.getLogger (LoggingAdapter.class.getName ());
    private static LoggingAdapter singleton;

    private LoggingAdapter() {
    }

    public static LoggingAdapter getInstance () {

        if (singleton == null) {
            singleton = new LoggingAdapter();
        }

        return singleton;
    }

    /*
     * Omdat we er in onze originele oplossing voor hadden gekozen om logs per dag te verzamelen, moeten we nog
     * steeds controleren of mogelijk een nieuwe dag is aangebroken (en dus een nieuwe logfile aangemaakt moet worden).
     */
    private void checkLogFile () {

        try {

            LoggingFormatter formatter = new LoggingFormatter ();

            // Als de logfile nog niet bestaat, worden eventueel gekoppelde FileHandlers verwijderd.
            if (formatter.logFileDoesNotExist ()) {
                for (Handler handler : LOGGER.getHandlers ()) {
                    handler.flush ();
                    LOGGER.removeHandler(handler);
                }
            }

            // Als er geen geldige FileHandlers zijn gekoppeld, gebeurt dat nu.
            if (LOGGER.getHandlers().length == 0) {
                FileHandler handler = new FileHandler(formatter.getFileForLogging().getPath (), true);
                handler.setFormatter(formatter);
                LOGGER.addHandler(handler);
                LOGGER.setUseParentHandlers(false);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    public void printLog (String message, Exception... e) {

        checkLogFile();

        if (e.length != 0) {
            LOGGER.log (Level.SEVERE, message, e [0]);
        }
        else {
            LOGGER.info(message);
        }
    }

    public void printLog (Exception e) {
        printLog (e.getMessage(), e);
    }
}
