package nationalid.exceptions;

import nationalid.helpers.LogManager;

public class LoggedException extends Throwable {

    public LoggedException(String message) {
        super(message);
        LogManager.LogMessage(message);
    }

    public LoggedException(Throwable cause) {
        super(cause);
        LogManager.LogMessage(cause);
    }

    public LoggedException(String message, Throwable cause) {
        super(message, cause);
        LogManager.LogMessage(message, cause);
    }

}
