import java.io.IOException;

/**
 * This Java code defines a class called SmartLamp that extends the Device class.
 * The SmartLamp class represents a smart lamp that can be controlled by a smart home system.
 * It has properties like kelvin, brightness, and methods to set and get these properties.
 */
public class SmartLamp extends Device {

    private int kelvin = 4000;
    private int brightness = 100;

    /**
     * Creates a new SmartLamp object with the given name and default initial status "off".
     *
     * @param name the name of the SmartLamp
     */
    public SmartLamp(String name) {
        super(name);
    }

    /**
     * Creates a new SmartLamp object with the given name and initial status.
     *
     * @param name          the name of the SmartLamp
     * @param initialStatus the initial status of the SmartLamp
     * @throws SmartHomeSystemException if there is an error with the input parameters
     * @throws IOException              if there is an I/O error while creating the SmartLamp
     */
    public SmartLamp(String name, String initialStatus) throws SmartHomeSystemException, IOException {
        super(name, initialStatus);

    }

    /**
     * Creates a new SmartLamp object with the specified name, initial status, and brightness value.
     *
     * @param name          the name of the SmartLamp
     * @param initialStatus the initial status of the SmartLamp
     * @param brightness    the brightness value of the SmartLamp, must be in the range of 0%-100%
     * @throws SmartHomeSystemException if the brightness value is not within the valid range of 0%-100%
     * @throws IOException              if there is an error in the input/output process
     */
    public SmartLamp(String name, String initialStatus, int brightness) throws SmartHomeSystemException, IOException {
        super(name, initialStatus);
        this.brightness = brightness;
    }

    /**
     * Creates a new SmartLamp object with the specified name, initial status, kelvin, and brightness values.
     *
     * @param name          the name of the SmartLamp
     * @param initialStatus the initial status of the SmartLamp
     * @param kelvin        the color temperature in kelvin units, must be in range of 2000K-6500K
     * @param brightness    the brightness of the SmartLamp, must be in range of 0%-100%
     * @throws SmartHomeSystemException if the kelvin or brightness values are outside the specified ranges
     * @throws IOException              if an I/O error occurs
     */
    public SmartLamp(String name, String initialStatus, int kelvin, int brightness) throws SmartHomeSystemException, IOException {
        super(name, initialStatus);

        if (2000 <= kelvin && kelvin <= 6500) {
            this.kelvin = kelvin;
        } else {
            throw new SmartHomeSystemException("Kelvin value must be in range of 2000K-6500K!");
        }
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        } else {
            throw new SmartHomeSystemException("Brightness must be in range of 0%-100%!");

        }


    }

    /**
     * Returns the current color temperature of the smart lamp in Kelvin.
     *
     * @return the current color temperature in Kelvin.
     */
    public Integer getKelvin() {
        return kelvin;
    }

    /**
     * Sets the kelvin value of the SmartLamp.
     *
     * @param kelvin the kelvin value to set
     * @throws SmartHomeSystemException if the kelvin value is not within the range of 2000K-6500K
     * @throws IOException              if there is an error writing to the file
     */
    public void setKelvin(int kelvin) throws SmartHomeSystemException, IOException {
        if (2000 <= kelvin && kelvin <= 6500) {
            this.kelvin = kelvin;
        } else {
            throw new SmartHomeSystemException("Kelvin value must be in range of 2000K-6500K!");
        }


    }

    /**
     * This method returns the brightness of the SmartLamp as an Integer.
     *
     * @return the brightness of the SmartLamp as an Integer
     */
    public Integer getBrightness() {
        return brightness;
    }

    /**
     * Sets the brightness level of the lamp.
     *
     * @param brightness an integer value representing the brightness level (0-100%)
     * @throws SmartHomeSystemException if the brightness value is out of range
     * @throws IOException              if there is an error writing to the file
     */
    public void setBrightness(int brightness) throws SmartHomeSystemException, IOException {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        } else {
            throw new SmartHomeSystemException("Brightness must be in range of 0%-100%!");
        }


    }

    /**
     * Sets the kelvin value and brightness of the lamp to the specified values.
     *
     * @param kelvin     the kelvin value to be set (between 2000K and 6500K)
     * @param brightness the brightness value to be set (between 0% and 100%)
     * @throws SmartHomeSystemException if kelvin or brightness are out of range
     * @throws IOException              if an I/O error occurs while writing to the log file
     */
    public void setWhite(int kelvin, int brightness) throws SmartHomeSystemException, IOException {
        this.setKelvin(kelvin);
        this.setBrightness(brightness);

    }

    /**
     * Returns a string representation of the Smart Lamp object.
     *
     * @return a formatted string containing the lamp's name, initial status, kelvin value, brightness percentage, and time to switch status.
     */
    public String toString() {
        return String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness, and its time to " +
                "switch its status is %s.", this.getName(), this.getInitialStatus().toLowerCase(), this.kelvin, this.brightness, this.getStrSwitchTime());
    }


}
