import java.io.IOException;
import java.text.ParseException;

/**
 * The Main class contains the main method of the SmartHomeSystem program.
 * This class reads a file containing a list of commands, creates a new SmartHomeSystem object, executes the commands,
 * and writes the output to a file.
 */

public class Main {

    public static void main(String[] args) throws ParseException, IOException {

        SmartHomeSystem smartHomeSystem = new SmartHomeSystem();
        smartHomeSystem.run(args[0], args[1]);

    }

}
