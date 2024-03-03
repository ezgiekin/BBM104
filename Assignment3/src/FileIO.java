import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class FileIO {

    public static ArrayList<String> readFromFile(String path) throws Exception {
        try {
            ArrayList<String> results = new ArrayList<>();
            for (String line : Files.readAllLines(Paths.get(path))) {
                if (!line.equals("")) { // if it is not empty add to list
                    results.add(line);
                }
            }


            return results;
        } catch (IOException e) {
            throw new Exception("File Error");
        }

    }

    public static void writeToFile(String content, String fileName) throws IOException {
        File file = new File(fileName);
        FileWriter fr = new FileWriter(file, true);
        fr.write(content + "\n");
        fr.close();
    }


    public static void cleanFile(String output) throws IOException {
        FileWriter writer = new FileWriter(output, false);
        writer.write("");
        writer.close();
    }

}

