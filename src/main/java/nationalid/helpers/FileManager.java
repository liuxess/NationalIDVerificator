package nationalid.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    public static Boolean CreateFile(String fileName) throws IOException {
        File targetFile = new File(fileName);
        if (targetFile.exists())
            throw new IOException("File already exists");

        Boolean createdSuccessfully = targetFile.createNewFile();
        return createdSuccessfully;
    }

    public static Boolean FileExists(String fileName) {
        File targetFile = new File(fileName);
        return targetFile.exists();
    }

    public static void WriteToFile(String fileName, String message) throws IOException {
        if (!FileExists(fileName))
            throw new IOException("File does not exist");

        WriteToExistingFile(fileName, message);
    }

    public static void ForceWriteToFile(String fileName, String message) throws IOException {
        if (!FileExists(fileName))
            CreateFile(fileName);

        WriteToExistingFile(fileName, message);
    }

    private static void WriteToExistingFile(String fileName, String message) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(message);
        fileWriter.close();
    }

    public static void DeleteFile(String fileName) {
        File targetFile = new File(fileName);
        targetFile.delete();
    }

    public static String ReadFromFile(String fileName) throws IOException {
        Path filePath = Path.of(fileName);

        return Files.readString(filePath);
    }

}
