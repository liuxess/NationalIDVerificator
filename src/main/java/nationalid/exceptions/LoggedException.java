package nationalid.exceptions;

import nationalid.loggers.LogManager;

/*
 * An Exception object that will try to log the message either to the passed LogManager or it's global instance
 *
 */
public class LoggedException extends Throwable {

    /**
     * Will log the Exception with the passed message and Manager
     * 
     * @param message    message to be thrown and logged
     * @param logManager LogManager that will log the errors
     * @see nationalid.loggers.LogManager
     */
    public LoggedException(String message, LogManager logManager) {
        super(message);
        logManager.LogMessage(message);
    }

    /**
     * Will log the Exception with the passed message through Global Instance for
     * LogManager
     * 
     * @param message to be logged
     */
    public LoggedException(String message) {
        this(message, LogManager.getGlobalInstance());
    }

    /**
     * Will log the Exception with the passed cause
     * using the passed LogManager
     * 
     * @param cause      of the Exception
     * @param logManager to log the Exception
     * @see nationalid.loggers.LogManager
     */
    public LoggedException(Throwable cause, LogManager logManager) {
        super(cause);
        logManager.LogMessage(cause);
    }

    /**
     * Will log the Exception with the passed cause
     * using the Global Instance of LogManager
     * 
     * @param cause of the Exception
     * @see nationalid.loggers.LogManager
     */
    public LoggedException(Throwable cause) {
        this(cause, LogManager.getGlobalInstance());
    }

    /**
     * Will log the Exception with the passed cause and message
     * using the passed instance of LogManager
     * 
     * @param message    to be logged
     * @param cause      of the Exception
     * @param logManager to log the Exception
     * @see nationalid.loggers.LogManager
     */
    public LoggedException(String message, Throwable cause, LogManager logManager) {
        super(message, cause);
        logManager.LogMessage(cause);
    }

    /**
     * Will log the Exception with the passed cause and message
     * using the Global Instance of LogManager
     * 
     * @param message to be logged
     * @param cause   of the Exception
     * @see nationalid.loggers.LogManager
     */
    public LoggedException(String message, Throwable cause) {
        this(message, cause, LogManager.getGlobalInstance());
    }

}
