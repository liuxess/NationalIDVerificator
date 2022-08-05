package nationalid.loggers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import nationalid.helpers.FileManager;
import nationalid.interfaces.Logable;

/**
 * Designed to log messages to a pysical file
 * 
 * @see nationalid.interfaces.Logable
 */
public class FileLogger implements Logable {

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.interfaces.Logable#LogMessage(java.lang.String)
     */
    @Override
    public void LogMessage(String message) throws Exception {
        LogToFile(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nationalid.interfaces.Logable#CompareInstance(nationalid.interfaces.Logable)
     */
    @Override
    public Boolean CompareInstance(Logable other) {
        return other instanceof FileLogger;
    }

    /**
     * Generates a name for the log file based on current day
     * 
     * @return generated file name
     */
    private static String GenerateLogName() {
        LocalDate currentDate = LocalDate.now();

        // the file ends with date representation 22.06.12 to be easily traceable
        String logFileName = String.format("nationalIDLog.%s.log",
                currentDate.format(
                        DateTimeFormatter.ofPattern("yy.MM.dd")));

        return logFileName;
    }

    /**
     * Attempts to log the message to a log file
     * 
     * @param message to log
     * @throws Exception if there were problems writing
     * @see nationalid.helpers.FileManager#ForceWriteToFile(String, String)
     */
    private static void LogToFile(String message) throws Exception {
        String fileName = GenerateLogName();
        try {
            FileManager.ForceWriteToFile(fileName, message);
        } catch (IOException ex) {
            throw new Exception(String.format("Unable to handle file logging: %s", ex.getMessage()));
        }
    }

}
