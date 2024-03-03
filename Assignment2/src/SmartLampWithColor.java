import java.io.IOException;

/**
 * This is a Java class called SmartLampWithColor, which extends the SmartLamp class.
 * It represents a smart lamp that can change its color in addition to its brightness and Kelvin value.
 */
public class SmartLampWithColor extends SmartLamp {
    private String colorCode = "";

    /**
     * Creates a new SmartLampWithColor object with the specified name and default initial status, Kelvin value, and brightness level.
     *
     * @param name the name of the SmartLampWithColor object
     */
    public SmartLampWithColor(String name) {
        super(name);
    }

    /**
     * Creates a new SmartLampWithColor object with the specified name and initial status.
     *
     * @param name          the name of the SmartLampWithColor object
     * @param initialStatus the initial status of the SmartLampWithColor object
     * @throws SmartHomeSystemException if the command is erroneous
     * @throws IOException              if an I/O error occurs while creating the SmartLampWithColor object
     */
    public SmartLampWithColor(String name, String initialStatus) throws SmartHomeSystemException, IOException {
        super(name, initialStatus);
    }

    /**
     * Creates a new SmartLampWithColor object with the specified name, initial status, Kelvin value, and brightness.
     *
     * @param name          the name of the SmartLampWithColor object
     * @param initialStatus the initial status of the SmartLampWithColor object
     * @param kelvin        the Kelvin value of the SmartLampWithColor object (an integer between 2700 and 6500)
     * @param brightness    the brightness level of the SmartLampWithColor object (an integer between 0 and 100)
     * @throws SmartHomeSystemException if the Kelvin value is not in the range of 2700-6500 or the command is erroneous
     * @throws IOException              if an I/O error occurs while creating the SmartLampWithColor object
     */
    public SmartLampWithColor(String name, String initialStatus, int kelvin, int brightness) throws SmartHomeSystemException, IOException {
        super(name, initialStatus, kelvin, brightness);
    }

    /**
     * Creates a new SmartLampWithColor object with the specified name, initial status, color code, and brightness.
     *
     * @param name          the name of the SmartLampWithColor object
     * @param initialStatus the initial status of the SmartLampWithColor object
     * @param colorCode     the color code value of the SmartLampWithColor object in hexadecimal format (e.g., 0xFF0000 for red)
     * @param brightness    the brightness level of the SmartLampWithColor object (an integer between 0 and 100)
     * @throws SmartHomeSystemException if the color code is not in the range of 0x0-0xFFFFFF or the command is erroneous
     * @throws IOException              if an I/O error occurs while creating the SmartLampWithColor object
     */
    public SmartLampWithColor(String name, String initialStatus, String colorCode, int brightness) throws SmartHomeSystemException, IOException {
        super(name, initialStatus, brightness);
        if (colorCode.matches("^0[xX][0-9a-fA-F]+")) {
            if (colorCode.matches("^0[xX][0-9a-fA-F]{6}$")) {
                this.colorCode = colorCode;
            } else {
                throw new SmartHomeSystemException("Color code value must be in range of 0x0-0xFFFFFF!");
            }
        } else {
            throw new SmartHomeSystemException("Erroneous command!");
        }


    }

    /**
     * Sets the color code value of the SmartLampWithColor object to the specified value.
     *
     * @param s the color code value to set in hexadecimal format
     * @throws SmartHomeSystemException if the color code is not in the range of 0x0-0xFFFFFF or the command is erroneous
     * @throws IOException              if an I/O error occurs while setting the color code value
     */
    public void setColorCode(String s) throws SmartHomeSystemException, IOException {
        if (s.matches("^0[xX][0-9a-fA-F]+")) {
            if (s.matches("^0[xX][0-9a-fA-F]{6}$")) {
                this.colorCode = s;
            } else {
                throw new SmartHomeSystemException("Color code value must be in range of 0x0-0xFFFFFF!");
            }

        } else {
            throw new SmartHomeSystemException("Erroneous command!");
        }
    }

    /**
     * Sets the color code value and brightness level of the SmartLampWithColor object to the specified values.
     *
     * @param s          the color code value to set in hexadecimal format
     * @param brightness the brightness level to set (an integer between 0 and 100)
     * @throws SmartHomeSystemException if the color code is not in the range of 0x0-0xFFFFFF or the command is erroneous
     * @throws IOException              if an I/O error occurs while setting the color code value or brightness level
     */
    public void setColor(String s, int brightness) throws SmartHomeSystemException, IOException {
        setBrightness(brightness);
        this.setColorCode(s);


    }

    /**
     * Returns a string representation of the SmartLampWithColor object, including its name, status, color code value, brightness level,
     * and time to switch status.
     *
     * @return a string representation of the SmartLampWithColor object
     */
    public String toString() {
        if (colorCode.isEmpty()) {
            return String.format("Smart Color Lamp %s is %s and its color value is %dK with %d%% brightness, and its time to switch its status is %s.",
                    this.getName(), this.getInitialStatus().toLowerCase(), this.getKelvin(), this.getBrightness(), this.getStrSwitchTime());
        }
        return String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness, and its time to switch its status is %s.",
                this.getName(), this.getInitialStatus().toLowerCase(), this.colorCode, this.getBrightness(), this.getStrSwitchTime());
    }


}
