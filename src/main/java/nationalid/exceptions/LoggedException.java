package nationalid.exceptions;

import nationalid.loggers.LogManager;

public class LoggedException extends Throwable {

    public LoggedException(String message) {
        super(message);
        LogManager.getGlobalInstance().LogMessage(message);
    }

    public LoggedException(Throwable cause) {
        super(cause);
        LogManager.getGlobalInstance().LogMessage(cause);
    }

    public LoggedException(String message, Throwable cause) {
        super(message, cause);
        LogManager.getGlobalInstance().LogMessage(message, cause);
    }

}
