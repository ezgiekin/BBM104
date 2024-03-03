import java.io.IOException;
import java.util.Date;

/**
 * The SmartCamera class represents a camera device that consumes storage space over time.
 * It extends the Device class and adds additional functionalities for storage consumption calculation and switching status.
 */
public class SmartCamera extends Device {
    private final double megabytesConsumedPerMinutes;

    private Date lastCameraTime;

    private double consumed = 0;

    /**
     * Creates a new SmartCamera object with the given name and megabytes consumed per second.
     *
     * @param name                        the name of the camera
     * @param megabytesConsumedPerMinutes the amount of megabytes consumed per second
     * @throws SmartHomeSystemException if the megabyte value is not positive
     * @throws IOException              if there is an error in the input/output operation
     */
    public SmartCamera(String name, double megabytesConsumedPerMinutes) throws SmartHomeSystemException, IOException {
        super(name);
        if (megabytesConsumedPerMinutes <= 0) {
            throw new SmartHomeSystemException("Megabyte value must be a positive number!");
        } else {
            this.megabytesConsumedPerMinutes = megabytesConsumedPerMinutes;
        }
    }

    /**
     * Creates a SmartCamera object with the specified name, megabytes consumed per second, and initial status.
     *
     * @param name                        the name of the SmartCamera
     * @param megabytesConsumedPerMinutes the amount of megabytes consumed per second by the SmartCamera (must be a positive number)
     * @param initialStatus               the initial status of the SmartCamera ("On" or "Off")
     * @throws SmartHomeSystemException if the megabytes consumed per second is not a positive number
     * @throws IOException              if an I/O error occurs
     */
    public SmartCamera(String name, double megabytesConsumedPerMinutes, String initialStatus) throws SmartHomeSystemException, IOException {
        super(name, initialStatus);
        if (megabytesConsumedPerMinutes <= 0) {
            throw new SmartHomeSystemException("Megabyte value must be a positive number!");
        } else {
            this.megabytesConsumedPerMinutes = megabytesConsumedPerMinutes;
        }
        if (initialStatus.equals("On")) {
            lastCameraTime = Time.initialTime;
        }

    }

    /**
     * Calculates the amount of storage consumed by the camera and updates the "consumed" variable.
     * The calculation is based on the time elapsed since the last update and the rate of megabytes consumed per second.
     */
    private void calculateBytes() {
        if (lastCameraTime == null) {
            this.consumed += 0;
        } else {
            double consumed = (megabytesConsumedPerMinutes) * ((Time.initialTime.getTime() - lastCameraTime.getTime()) / (double) (1000 * 60));
            this.consumed += consumed;
        }

    }

    /**
     * This method is used to switch on or off the Smart Camera.
     * If the new status is "Off", it will calculate the amount of megabytes consumed since the last time it was switched on
     * and set lastCameraTime to null.
     * If the new status is "On", it will set lastCameraTime to the current time.
     *
     * @param s a String representing the new status to be set ("On" or "Off")
     * @throws SmartHomeSystemException if the input status is not "On" or "Off"
     * @throws IOException              if an I/O error occurs
     */
    public void switchOnOff(String s) throws SmartHomeSystemException, IOException {
        super.switchOnOff(s);
        if (s.equals("Off")) {
            calculateBytes();
            lastCameraTime = null;
        } else {
            this.lastCameraTime = Time.initialTime;
        }

    }

    /**
     * This method switches on/off the camera and updates its status accordingly.
     * If the camera is switched off, it calculates the bytes and sets the lastCameraTime to null.
     * If the camera is switched on, it sets the lastCameraTime to the current time.
     *
     * @throws SmartHomeSystemException if there is an error with the Smart Home System.
     * @throws IOException              if there is an error with input/output operations.
     */
    public void switchOnOff() throws SmartHomeSystemException, IOException {
        super.switchOnOff();
        if (this.getInitialStatus().equals("Off")) {
            calculateBytes();
            lastCameraTime = null;
        } else {
            this.lastCameraTime = Time.initialTime;
        }

    }

    /**
     * Switches off the smart camera and calculates the total amount of storage consumed so far
     * based on the time elapsed since the last switch-on. Sets the last switch time to null.
     */
    public void switchOff() {
        super.switchOff();
        calculateBytes();
        lastCameraTime = null;
    }


    /**
     * Returns a string representation of the SmartCamera object.
     *
     * @return a string containing the camera name, status, storage consumed and time to switch status
     */
    public String toString() {
        return String.format("Smart Camera %s is %s and used %.2f MB of storage so far (excluding current status), and its time to switch its status is %s.", this.getName(), this.getInitialStatus().toLowerCase(), this.consumed, this.getStrSwitchTime());

    }

}
