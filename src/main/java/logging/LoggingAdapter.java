package logging;

import java.util.logging.*;

public class LoggingAdapter {

    private static final Logger LOGGER = Logger.getLogger (LoggingAdapter.class.getName ());
    private static LoggingAdapter singleton;
    private LoggingFileHandler fileHandler;

    private LoggingAdapter() {
        fileHandler = new LoggingFileHandler (LOGGER);
    }

    public static LoggingAdapter getInstance () {

        if (singleton == null) {
            singleton = new LoggingAdapter();
        }

        return singleton;
    }

    public void printLog (String message, Exception... e) {

        // Voor elke dag wordt een nieuwe log-file aangemaakt.
        fileHandler.checkNextDay();

        if (e.length != 0) {
            LOGGER.log (Level.SEVERE, message, e [0]);
        }
        else {
            LOGGER.info(message);
        }

        // Loginfo wordt direct weggeschreven naar file.
        fileHandler.flush ();
    }

    public void printLog (Exception e) {
        printLog (e.getMessage(), e);
    }
}