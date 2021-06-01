package logging;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.*;

public class LoggingFileHandler {

    private final Logger logger;

    /*
     * Om een nieuwe file te kunnen koppelen aan de logger (omdat
     * bij de start van een nieuwe dag een nieuwe file wordt
     * aangemaakt), moet deze bekend zijn.
     */
    public LoggingFileHandler (Logger logger) {
        this.logger = logger;
    }

    private String getFileNameForLogging () {
        String filename = LoggingDateFormatter.getFormattedDate (LocalDateTime.now ());
        return "src\\main\\resources\\logging\\" + filename + ".log";
    }

    protected File getFileForLogging () {
        return new File (getFileNameForLogging ());
    }

    /*
     * Er wordt gecontroleerd of de file nog niet bestaat en/of
     * als de file al bestaat wordt gecontroleerd of er al
     * informatie in de file staat.
     */
    protected boolean logFileIsEmpty () {
        File file = getFileForLogging ();
        return !file.exists () || (file.length () == 0);
    }

    /*
     * De file-buffer voor alle handlers wordt leeg gemaakt (de
     * buffer wordt geforceerd om zichzelf te legen naar file).
     */
    protected void flush () {

        for (Handler handler : logger.getHandlers ()) {
            handler.flush ();
        }
    }

    /*
     * Omdat we er in onze originele oplossing voor hadden gekozen om logs per dag te verzamelen, moeten we nog
     * steeds controleren of mogelijk een nieuwe dag is aangebroken (en dus een nieuwe logfile aangemaakt moet worden).
     */
    protected void checkNextDay () {

        try {

            // Als de logfile nog niet bestaat of nog leeg is, worden eventueel gekoppelde FileHandlers verwijderd.
            if (logFileIsEmpty ()) {
                for (Handler handler : logger.getHandlers ()) {
                    handler.flush ();
                    logger.removeHandler (handler);
                }
            }

            // Als er geen geldige FileHandlers zijn gekoppeld, gebeurt dat nu.
            if (logger.getHandlers().length == 0) {
                FileHandler fileHandler = new FileHandler (getFileForLogging ().getPath (), true);
                fileHandler.setFormatter (new LoggingFormatter (this));
                logger.addHandler (fileHandler);
                logger.setUseParentHandlers (false);
            }
        }
        catch (IOException ex) {
            ex.printStackTrace ();
        }
    }
}