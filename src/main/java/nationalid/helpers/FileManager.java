package nationalid.helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Class to hold static methods for various shortcuts to file management
 */
public class FileManager {

    // region: publicly exposed methods

    /**
     * Tries to create a file with the specified file name
     * 
     * @param fileName name of file to be created
     * @return whether the file was created
     * @throws IOException for file creation
     * @see java.io.File
     */
    public static Boolean CreateFile(String fileName) throws IOException {
        File targetFile = new File(fileName);
        if (targetFile.exists())
            throw new IOException("File already exists");

        Boolean createdSuccessfully = targetFile.createNewFile();
        return createdSuccessfully;
    }

    /**
     * Verifies if a File exists
     * 
     * @param fileName name of file to be checked
     * @return Whether or not the file exists
     * @see java.io.File
     */
    public static Boolean FileExists(String fileName) {
        File targetFile = new File(fileName);
        return targetFile.exists();
    }

    /**
     * Writes a message in a new line in the file
     * 
     * @param fileName name of file to write to
     * @param message  to be recorded in the file
     * @throws IOException for writing in the file, ie.: File not existing
     */
    public static void WriteToFile(String fileName, String message) throws IOException {
        if (!FileExists(fileName))
            throw new IOException("File does not exist");

        WriteToExistingFile(fileName, message);
    }

    /**
     * Writes a message in a new line in the file. If the file does not exist,
     * creates it
     * 
     * @param fileName name of file to be written in
     * @param message  to be written in the file
     * @throws IOException for writing in the file, ie.: no permissions
     */
    public static void ForceWriteToFile(String fileName, String message) throws IOException {
        if (!FileExists(fileName))
            CreateFile(fileName);

        WriteToExistingFile(fileName, message);
    }

    /**
     * Deletes the specified file
     * 
     * @param fileName name of file to be deleted
     * @return whether or not the deletion is a success
     * @see java.io.File
     */
    public static Boolean DeleteFile(String fileName) {
        File targetFile = new File(fileName);
        return targetFile.delete();
    }

    /**
     * Reads the contents of the file into a string
     * 
     * @param fileName name of file to be read
     * @return content as String
     * @throws IOException with problems on opening/reading
     * @see java.io.File
     */
    public static String ReadFromFile(String fileName) throws IOException {
        Path filePath = Path.of(fileName);

        return Files.readString(filePath);
    }

    // endregion

    // region: private methods

    private static void WriteToExistingFile(String fileName, String message) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(message);
        fileWriter.close();
    }

    // endregion

}
