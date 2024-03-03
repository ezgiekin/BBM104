import java.io.IOException;
import java.util.Date;

/**
 * The SmartPlug class is a subclass of the Device class and represents a smart plug device in a smart home system.
 * It has several instance variables including the amperage of the device,
 * the state of the plug (whether it is plugged or unplugged), the time when the device was last plugged in,
 * and the wattage consumed by the device.
 */
public class SmartPlug extends Device {
    private double ampere;
    private String plugState = "Unplugged";

    private Date lastPlugTime;

    private double watts = 0;


    /**
     * Constructor to create a Smart Plug object with only name provided.
     *
     * @param name the name of the Smart Plug device
     */
    public SmartPlug(String name) {
        super(name);
    }

    /**
     * Constructor to create a Smart Plug object with name and initial status provided.
     *
     * @param name          the name of the Smart Plug device
     * @param initialStatus the initial status of the Smart Plug device, either "On" or "Off"
     * @throws SmartHomeSystemException if an invalid initial status is provided
     * @throws IOException              if there is an error with IO operations
     */
    public SmartPlug(String name, String initialStatus) throws SmartHomeSystemException, IOException {
        super(name, initialStatus);
        if (initialStatus.equals("On")) {
            lastPlugTime = Time.initialTime;
        }
    }

    /**
     * Constructor to create a Smart Plug object with name, initial status, and maximum amperage provided.
     *
     * @param name          the name of the Smart Plug device
     * @param initialStatus the initial status of the Smart Plug device, either "On" or "Off"
     * @param ampere        the maximum amperage that this Smart Plug can handle
     * @throws SmartHomeSystemException if an invalid initial status or amperage is provided
     * @throws IOException              if there is an error with IO operations
     */
    public SmartPlug(String name, String initialStatus, double ampere) throws SmartHomeSystemException, IOException {
        this(name, initialStatus);
        if (ampere <= 0) {
            throw new SmartHomeSystemException("Ampere value must be a positive number!");
        } else {
            this.ampere = ampere;
            this.plugState = "Plugged";
            if (initialStatus.equals("On")) {
                lastPlugTime = Time.initialTime;
            }

        }

    }

    /**
     * Calculates the energy consumption in watts of the smart plug based on its ampere,
     * the time it has been plugged in, and the voltage of 220V.
     * The calculated energy consumption is added to the existing consumption of the plug.
     */
    private void calculateWatt() {
        if (lastPlugTime == null) {
            this.watts += 0;
        } else {
            double calc = 220 * ampere * ((Time.initialTime.getTime() - lastPlugTime.getTime()) / (double) (1000 * 60 * 60));
            this.watts += calc;
        }

    }

    /**
     * Plugs in an item to the Smart Plug device and sets the amperage value.
     *
     * @param ampere the amperage of the item being plugged in
     * @throws SmartHomeSystemException if an item is already plugged in or an invalid amperage is provided
     * @throws IOException              if there is an error with IO operations
     */
    public void plugIn(int ampere) throws SmartHomeSystemException, IOException {
        if (this.plugState.equals("Plugged")) {
            throw new SmartHomeSystemException("There is already an item plugged in to that plug!");
        } else if (ampere <= 0) {
            throw new SmartHomeSystemException("Ampere value must be a positive number!");
        } else {
            this.plugState = "Plugged";
            this.ampere = ampere;
            if (this.getInitialStatus().equals("On")) {
                this.lastPlugTime = Time.initialTime;
            }
        }

    }

    /**
     * This method unplugs the device from the smart plug and calculates the wattage consumed by the device.
     * If there is no device currently plugged in, an error will be thrown.
     *
     * @throws SmartHomeSystemException If there is no device currently plugged in
     * @throws IOException              If there is an I/O error
     */
    public void plugOut() throws SmartHomeSystemException, IOException {
        if (this.plugState.equals("Unplugged")) {
            throw new SmartHomeSystemException("This plug has no item to plug out from that plug!");
        } else {
            this.plugState = "Unplugged";
            calculateWatt();
            lastPlugTime = null;
        }
    }

    /**
     * Changes the switch status of the smart plug to the provided status string, either "On" or "Off".
     * Calculates wattage consumption based on the time since the plug was last switched on, or since the device was unplugged.
     * If the provided status is "Off", the plug's wattage consumption is calculated and the last plug time is set to null.
     * If the provided status is "On", the last plug time is set to the current time.
     *
     * @param s a string representing the desired switch status of the smart plug, either "On" or "Off"
     * @throws SmartHomeSystemException if the provided status is not valid
     * @throws IOException              if there is an error with the input/output operations
     */
    public void switchOnOff(String s) throws SmartHomeSystemException, IOException {
        super.switchOnOff(s);
        if (s.equals("Off")) {
            calculateWatt();
            lastPlugTime = null;
        } else {
            if (plugState.equals("Plugged")) {
                this.lastPlugTime = Time.initialTime;
            }

        }
    }

    /**
     * Switches the status of the smart plug and updates the wattage calculation based on the current status.
     * If the smart plug is switched off, it will calculate the wattage consumed until now and reset the last plug time.
     * If the smart plug is switched on, it will update the last plug time to the current time.
     *
     * @throws SmartHomeSystemException if an error occurs while switching the status
     * @throws IOException              if an error occurs while updating the wattage calculation
     */
    public void switchOnOff() throws SmartHomeSystemException, IOException {
        super.switchOnOff();
        if (this.getInitialStatus().equals("Off")) {
            calculateWatt();
            lastPlugTime = null;
        } else {
            if (plugState.equals("Plugged")) {
                this.lastPlugTime = Time.initialTime;
            }

        }
    }

    /**
     * Turns off the smart plug and calculates the total energy consumption since the last plug-in time.
     * If the plug is already off, no action is taken.
     */
    public void switchOff() {
        super.switchOff();
        calculateWatt();
        lastPlugTime = null;
    }

    /**
     * Returns a string representation of this SmartPlug object, providing information about the plug's status and power consumption.
     *
     * @return A string representation of this SmartPlug object.
     */
    public String toString() {
        return String.format("Smart Plug %s is %s and consumed %.2fW so far (excluding current device), and its time to switch its status is %s.", this.getName(), this.getInitialStatus().toLowerCase(), this.watts, this.getStrSwitchTime());
    }


}
