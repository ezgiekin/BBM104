import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**

 The FileIO class provides methods for reading from and writing to files.
 */
public class FileIO {

    /**
     * Reads a file located at the given path and returns its contents as an ArrayList of strings, where each string represents a line in the file.
     *
     * @param path the path to the file to be read
     * @return an ArrayList of strings, where each string represents a line in the file
     */
    public static ArrayList<String> readFromFile(String path) throws IOException, SmartHomeSystemException {
        try {
            ArrayList<String> results = new ArrayList<>();
            for (String line : Files.readAllLines(Paths.get(path))) {
                if (!line.equals("")) { // if it is not empty add to list
                    results.add(line);
                }
            }


            return results;
        } catch (IOException e) {
            throw new SmartHomeSystemException("Command file does not exist!");
        }

    }

    /**
     * Writes the given content to a file named "output.txt" located in the current working directory.
     *
     * @param content the content to be written to the file
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public static void writeToFile(String content,String fileName) throws IOException {
        File file = new File(fileName);
        FileWriter fr = new FileWriter(file, true);
        fr.write(content + "\n");
        fr.close();
    }

    /**
     * This method is used to clean the content of a file named "output.txt".
     * It opens the file in write mode and overwrites it with an empty string.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */
    public static void cleanFile(String output) throws IOException {
        FileWriter writer = new FileWriter(output, false);
        writer.write("");
        writer.close();
    }

}

