package nationalid.loggers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import nationalid.interfaces.Logable;

/**
 * A class to manage Logable objects.
 * Can provide a global instance to be in different places with same Logable
 * objects
 * 
 * @see nationalid.interfaces.Logable
 */
public class LogManager {

    private static final LogManager globalInstance = new LogManager();
    private List<Logable> registeredLoggers = new ArrayList<>();
    private Boolean throwExceptionOnTotalFailure = true;

    // region: Constructors

    /**
     * Will initialize a Log Manager for logging messages
     * 
     * @param throwExceptionOnTotalFailure - will throw a Runtime Exception if all
     *                                     registered loggers fail
     * @param loggers                      an Array of Logable objects that should
     *                                     be used for logging
     * @see nationalid.interfaces.Logable
     */
    public LogManager(Boolean throwExceptionOnTotalFailure, Logable... loggers) {
        this.throwExceptionOnTotalFailure = throwExceptionOnTotalFailure;

        if (loggers.length == 0)
            return;
        // else
        Arrays.stream(loggers).forEach(logger -> registeredLoggers.add(logger));
    }

    /**
     * Will initialize a Log Manager for logging messages
     * 
     * @param throwExceptionOnTotalFailure - will throw a Runtime Exception if all
     *                                     registered loggers fail
     * @see nationalid.interfaces.Logable
     */
    public LogManager(Boolean throwExceptionOnTotalFailure) {
        this(throwExceptionOnTotalFailure, new Logable[] {});
    }

    /**
     * Will initialize a Log Manager for logging messages.
     * Will throw a RuntimeException if all loggers fail
     * 
     * @param loggers an Array of Logable objects that should
     *                be used for logging
     * @see nationalid.interfaces.Logable
     */
    public LogManager(Logable... loggers) {
        this(true, loggers);
    }

    /**
     * Will initialize a Log Manager for logging messages.
     * Will throw a RuntimeException if all registered loggers fail
     * 
     * @see nationalid.interfaces.Logable
     */
    public LogManager() {
        this(true);
    }
    // endregion

    // region: Message Logging

    /**
     * Logs the message using registered loggers, ignoring provided ones
     * 
     * @param message        to be logged
     * @param ignoredLoggers will be ignored from the registered list
     */
    public void LogMessage(String message, Logable... ignoredLoggers) {
        sendMessageToLoggers(message, ignoredLoggers);
    }

    /**
     * Logs the message using registered loggers
     * 
     * @param message to be logged
     */
    public void LogMessage(String message) {
        message = AddLogMetadata(message);
        LogMessage(message, new Logable[] {});
    }

    /**
     * Logs the exception using registered loggers
     * 
     * @param ex to be logged
     */
    public void LogMessage(Throwable ex) {
        LogMessage(String.format("%s; StackTrace: %s", ex.getMessage(), ex.getStackTrace().toString()));
    }

    /**
     * Logs the message and exception using registered loggers
     * 
     * @param message to be logged
     * @param ex      to be logged
     */
    public void LogMessage(String message, Throwable ex) {
        LogMessage(String.format("%s; StackTrace: %s", message, ex.getStackTrace().toString()));
    }

    // endregion

    // region: other public methods

    /**
     * Registeres a new logger to the list. Only one unique type of logger allowed.
     * 
     * @param logger implementation of Logable to be added to the list
     * @see nationalid.interfaces.Logable
     */
    public void addLogger(Logable logger) {
        if (registeredLoggers.parallelStream().anyMatch(registered -> registered.CompareInstance(logger))) {
            LogMessage(String.format("Tried to add a duplicate logger of %s", logger.getClass()));
            return;
        }

        registeredLoggers.add(logger);
    }

    /**
     * @return globally accessible instance of Log Manager
     */
    public static LogManager getGlobalInstance() {
        return globalInstance;
    }

    // endregion

    // region: private methods
    private void sendMessageToLoggers(String message, Logable... ignoredLoggers) {
        if (registeredLoggers.size() == 0)
            return; // No loggers registered, so logs die silently

        List<Logable> usableLoggers = registeredLoggers
                .parallelStream()
                .filter(isNotInIgnoredList(ignoredLoggers))
                .toList();

        if (usableLoggers.size() == 0) {
            if (!throwExceptionOnTotalFailure)
                return;

            throw new RuntimeException(String.format("There are no available loggers for errors: %s", message));
        }

        List<Logable> newIgnoredLoggers = new ArrayList<>();
        ArrayList<String> loggerIssues = new ArrayList<>();

        usableLoggers.forEach(sendMessageToLogger(message, newIgnoredLoggers, loggerIssues));

        if (newIgnoredLoggers.size() == 0)
            return;

        newIgnoredLoggers.addAll(Arrays.asList(ignoredLoggers));
        sendMessageToLoggers(String.join("\n\r", loggerIssues), newIgnoredLoggers.toArray(new Logable[0]));
    }

    private String AddLogMetadata(String message) {
        String ammendedMessage = String.format(
                "\r\n %s %s",
                LocalDateTime.now().toString(),
                message);

        return ammendedMessage;
    }

    private Consumer<? super Logable> sendMessageToLogger(String message, List<Logable> newIgnoredLoggers,
            ArrayList<String> loggerIssues) {
        return logger -> {
            try {
                logger.LogMessage(message);
            } catch (Exception ex) {
                newIgnoredLoggers.add(logger);
                loggerIssues.add(ex.toString());
            }
        };
    }

    private Predicate<? super Logable> isNotInIgnoredList(Logable... ignoredLoggers) {
        return logger -> Arrays.stream(ignoredLoggers)
                .noneMatch(iLogger -> logger.CompareInstance(iLogger));
    }

    // endregion
}
