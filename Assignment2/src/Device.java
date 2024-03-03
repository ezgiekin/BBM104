import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * The Device class represents a smart device that can be switched on and off.
 * It provides methods to get and set the device's name, status, and switch time, as well as toggle the status on and off.
 */
public abstract class Device extends SmartHomeSystem {
    private String name;
    private String status = "Off";
    private Date switchTime;

    public Device(){}

    /**
     * Creates a Device object with the given name and default initial status "Off".
     *
     * @param name the name of the device
     */
    public Device(String name) {
        this.name = name;
    }

    /**
     * Creates a new Device object with the given name and initial status.
     *
     * @param name          the name of the device
     * @param initialStatus the initial status of the device, either "On" or "Off"
     * @throws SmartHomeSystemException if the initialStatus parameter is not "On" or "Off"
     * @throws IOException              if an I/O error occurs while reading or writing data
     */
    public Device(String name, String initialStatus) throws SmartHomeSystemException, IOException {
        this.name = name;
        if (initialStatus.equals("On") || initialStatus.equals("Off")) {
            this.status = initialStatus;
        } else {
            throw new SmartHomeSystemException("Erroneous command!");
        }
    }

    /**
     * Returns the name of the Smart Device.
     *
     * @return the name of the Smart Device
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the switch time of the device.
     *
     * @return the switch time of the device as a Date object.
     */
    public Date getSwitchTime() {
        return switchTime;
    }

    /**
     * Sets the switch time for the Smart Home System.
     *
     * @param s       A string representing the switch time in the format "yyyy-MM-dd_HH:mm:ss", or "null" to indicate that there is no switch time.
     * @param devices An ArrayList of Device objects that will be affected by the switch time.
     * @throws ParseException           if the given string cannot be parsed as a valid date.
     * @throws IOException              if an I/O error occurs while setting the switch time.
     * @throws SmartHomeSystemException if the switch time is in the past.
     */
    public void setSwitchTime(String s, ArrayList<Device> devices) throws ParseException, IOException, SmartHomeSystemException {
        if (s.equals("null")) {
            this.switchTime = null;
        } else if (CheckAndLook.checkDate(s)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            if (formatter.parse(s).before(Time.initialTime)) {
                throw new SmartHomeSystemException("Switch time cannot be in the past!");
            } else if (formatter.parse(s).equals(Time.initialTime)) {
                this.switchTime = formatter.parse(s); // switch the device momentarily to sort it
                devices.sort(new DeviceComparator(devices));
                this.switchTime = null;
                this.switchOnOff();
                devices.sort(new DeviceComparator(devices));
            } else if(formatter.parse(s).equals(this.switchTime)){
                throw new SmartHomeSystemException("Switch time is the same as before, nothing changed!");
            }else {
                this.switchTime = formatter.parse(s);
            }

        }

    }

    /**
     * Returns the switch time of the device as a formatted string in the format "yyyy-MM-dd_HH:mm:ss".
     * If the switch time is null, returns null.
     *
     * @return a string representation of the switch time in the format "yyyy-MM-dd_HH:mm:ss", or null if the switch time is null.
     */
    public String getStrSwitchTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        if (switchTime == null) {
            return null;
        }
        return formatter.format(this.switchTime);
    }

    /**
     * Returns the initial status of the device.
     *
     * @return a string representing the initial status of the device ("On" or "Off").
     */
    public String getInitialStatus() {
        return status;
    }

    /**
     * sets the status of the device
     *
     * @param s "On" or "Off" status
     */

    public void setStatus(String s) {
        this.status = s;
    }


    /**
     * changes the name of the device
     *
     * @param name new name of the device
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Switches the status of the device on or off depending on the input string.
     *
     * @param s a string representing the status to switch to ("On" or "Off")
     * @throws SmartHomeSystemException if the input string is not "On" or "Off" or if the device is already in the requested status
     * @throws IOException              if there is an error writing to the output file
     */
    public void switchOnOff(String s) throws SmartHomeSystemException, IOException {
        if (this.status.equals(s)) {
            if (s.equals("On")) {
                throw new SmartHomeSystemException("This device is already switched on!");
            } else {
                throw new SmartHomeSystemException("This device is already switched off!");
            }
        } else {
            this.status = s;
            this.switchTime = null;
        }
    }

    /**
     * Toggles the status of the device to the opposite of its current status.
     * If the current status is "Off", it will be changed to "On", and vice versa.
     *
     * @throws SmartHomeSystemException if there is an error with the command
     * @throws IOException              if there is an error with writing to the output file
     */
    public void switchOnOff() throws SmartHomeSystemException, IOException {
        if (this.status.equals("Off")) {
            this.setStatus("On");
        } else {
            this.setStatus("Off");
        }
        this.switchTime = null;
    }

    /**
     * Switches off the device.
     * Used by Remove command
     */
    public void switchOff() {
        this.status = "Off";
        this.switchTime = null;
    }

    public abstract String toString();
}
