package uk.ac.soton.comp2211.utility;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionMonitor {

    private static final Logger LOGGER = Logger.getLogger(ExceptionMonitor.class.getName());

    public void logException(Exception e, Level level) {
        LOGGER.log(level, "Exception occurred", e);

        classifyException(e);
    }

    private void classifyException(Exception e) {
        if (e instanceof java.io.IOException) {
            LOGGER.log(Level.WARNING, "IO Exception detected", e);
        } else if (e instanceof NullPointerException) {
            LOGGER.log(Level.SEVERE, "Null Pointer Exception detected", e);
        } else {
            LOGGER.log(Level.INFO, "Unhandled exception type", e);
        }
    }

}


