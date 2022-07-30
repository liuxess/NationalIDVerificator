package nationalid.loggers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;

import nationalid.interfaces.Logable;

public class LogManager {

    private static LogManager globalInstance = new LogManager();
    private List<Logable> registeredLoggers = new ArrayList<>();;
    private Boolean throwExceptionOnTotalFailure = true;

    public LogManager(Boolean throwExceptionOnTotalFailure, Logable... loggers) {
        this.throwExceptionOnTotalFailure = throwExceptionOnTotalFailure;

        if (loggers.length > 0)
            Arrays.stream(loggers).forEach(logger -> registeredLoggers.add(logger));
    }

    public LogManager(Boolean throwExceptionOnTotalFailure) {
        this(throwExceptionOnTotalFailure, new Logable[] {});
    }

    public LogManager(Logable... loggers) {
        this(true, loggers);
    }

    public LogManager() {
        this(true);
    }

    private String AddLogMetadata(String message) {
        String ammendedMessage = String.format(
                "\r\n %s %s",
                LocalDateTime.now().toString(),
                message);

        return ammendedMessage;
    }

    public void LogMessage(String message, Logable... ignoredLoggers) {
        sendMessageToLoggers(message, ignoredLoggers);
    }

    public void LogMessage(String message) {
        message = AddLogMetadata(message);
        LogMessage(message, new Logable[] {});
    }

    public void LogMessage(Throwable ex) {
        LogMessage(String.format("%s; StackTrace: %s", ex.getMessage(), ex.getStackTrace().toString()));
    }

    public void LogMessage(String message, Throwable ex) {
        LogMessage(String.format("%s; StackTrace: %s", message, ex.getStackTrace().toString()));
    }

    public void addLogger(Logable logger) {
        if (registeredLoggers.parallelStream().anyMatch(registered -> registered.CompareInstance(logger))) {
            LogMessage(String.format("Tried to add a duplicate logger of %s", logger.getClass()));
            return;
        }

        registeredLoggers.add(logger);
    }

    void sendMessageToLoggers(String message, Logable... ignoredLoggers) {
        List<Logable> usableLoggers = registeredLoggers
                .parallelStream()
                .filter(logger -> Arrays.stream(ignoredLoggers)
                        .noneMatch(iLogger -> logger.CompareInstance(iLogger)))
                .toList();

        if (usableLoggers.size() == 0) {
            if (!throwExceptionOnTotalFailure)
                return;

            throw new RuntimeException(String.format("There are no available loggers for errors: %s", message));
        }

        List<Logable> newIgnoredLoggers = new ArrayList<>();
        ArrayList<String> loggerIssues = new ArrayList<>();

        usableLoggers.forEach(logger -> {
            try {
                logger.LogMessage(message);
            } catch (Exception ex) {
                newIgnoredLoggers.add(logger);
                loggerIssues.add(ex.toString());
            }
        });

        if (newIgnoredLoggers.size() == 0)
            return;

        newIgnoredLoggers.addAll(Arrays.asList(ignoredLoggers));
        sendMessageToLoggers(String.join("\n\r", loggerIssues), newIgnoredLoggers.toArray(new Logable[0]));
    }

    public static LogManager getGlobalInstance() {
        return globalInstance;
    }

}
