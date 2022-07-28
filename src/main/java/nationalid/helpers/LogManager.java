package nationalid.helpers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {

    private static String GenerateLogName() {
        LocalDate currentDate = LocalDate.now();

        // the file ends with date representation 22.06.12 to be easily traceable
        String logFileName = String.format("nationalIDLogFile.%s",
                currentDate.format(
                        DateTimeFormatter.ofPattern("yy.MM.dd")));

        return logFileName;
    }

    private static String AddLogMetadata(String message) {
        String ammendedMessage = String.format(
                "\r\n %s %s",
                LocalDateTime.now().toString(),
                message);

        return ammendedMessage;
    }

    public static void LogMessage(String message) {
        message = AddLogMetadata(message);
        String fileName = GenerateLogName();
        try {
            FileManager.ForceWriteToFile(fileName, message);
        } catch (IOException ex) {
            System.out.println(String.format("Unable to handle file logging: %s", ex.getMessage()));
        }
    }

    public static void LogMessage(Throwable ex) {
        LogMessage(String.format("%s; StackTrace: %s", ex.getMessage(), ex.getStackTrace().toString()));
    }

    public static void LogMessage(String message, Throwable ex) {
        LogMessage(String.format("%s; StackTrace: %s", message, ex.getStackTrace().toString()));
    }
}
