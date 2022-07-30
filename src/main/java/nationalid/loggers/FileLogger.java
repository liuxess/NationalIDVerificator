package nationalid.loggers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import nationalid.helpers.FileManager;
import nationalid.interfaces.Logable;

public class FileLogger implements Logable {

    @Override
    public void LogMessage(String message) throws Exception {
        LogToFile(message);
    }

    private static String GenerateLogName() {
        LocalDate currentDate = LocalDate.now();

        // the file ends with date representation 22.06.12 to be easily traceable
        String logFileName = String.format("nationalIDLog.%s.log",
                currentDate.format(
                        DateTimeFormatter.ofPattern("yy.MM.dd")));

        return logFileName;
    }

    private static void LogToFile(String message) throws Exception {
        String fileName = GenerateLogName();
        try {
            FileManager.ForceWriteToFile(fileName, message);
        } catch (IOException ex) {
            throw new Exception(String.format("Unable to handle file logging: %s", ex.getMessage()));
        }
    }

    @Override
    public Boolean CompareInstance(Logable other) {
        return other instanceof FileLogger;
    }
}
