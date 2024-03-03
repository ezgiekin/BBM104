import java.io.IOException;
/**

 A custom exception that is thrown when an error occurs in the Smart Home System.
 */
public class SmartHomeSystemException extends Exception{
    public SmartHomeSystemException(String message) throws IOException {
        super("ERROR: " + message);
    }
}
