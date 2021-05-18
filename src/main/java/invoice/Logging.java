package invoice;

import logging.LoggingAdapter;

/*
 * Deze Class wordt gebruikt om voor logging terug te kunnen vallen op een verkorte vorm.
 */
public class Logging {

    public static void printLog (String message) {
        LoggingAdapter.getInstance ().printLog (message);
    }
}