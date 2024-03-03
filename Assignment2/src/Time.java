import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This is a Java class that defines the Time component of a Smart Home System.
 * The Time class extends the SmartHomeSystem class and contains methods for setting and updating the time in the system,
 * as well as updating the status of the devices in the system based on the new time.
 */
public class Time extends SmartHomeSystem {

    public static Date initialTime;

    /**
     * Returns a formatted string representation of the initial time in the format "yyyy-MM-dd_HH:mm:ss".
     *
     * @return A string representation of the initial time.
     */
    public static String getStrTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return formatter.format(Time.initialTime);
    }

    /**
     * Sets the initial time of the system based on the provided string in the format of "yyyy-MM-dd_HH:mm:ss".
     * If the provided string has an invalid format, it throws a CustomError and terminates the program.
     * It also writes the success message to the file using the FileIO class.
     *
     * @param s a string in the format of "yyyy-MM-dd_HH:mm:ss" representing the initial time of the system.
     * @throws ParseException           if the provided string cannot be parsed into a valid date object.
     * @throws SmartHomeSystemException if the provided string has an invalid format.
     * @throws IOException              if an I/O error occurs while writing to the file.
     */
    public static void setInitialTime(String s, String outFileName) throws ParseException, SmartHomeSystemException, IOException {
        if (CheckAndLook.checkDate(s)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            initialTime = formatter.parse(s);
            FileIO.writeToFile("SUCCESS: Time has been set to " + Time.getStrTime() + "!", outFileName);
        } else {
            throw new SmartHomeSystemException("Format of the initial date is wrong! Program is going to terminate!");
        }

    }

    /**
     * This method takes an ArrayList of Devices as input, and sets the switch time and status of each device
     * according to the new time that has been set. The method sorts the devices by their switch time and switches
     * the first device that has a switch time before or equal to the new time.
     *
     * @param array an ArrayList of Devices to be updated with the new time
     * @throws ParseException           when there is an error parsing the time
     * @throws SmartHomeSystemException when there is an error with the Devices or time
     * @throws IOException              when there is an error with the input/output
     */
    private static void deviceSetter(ArrayList<Device> array) throws ParseException, SmartHomeSystemException, IOException {
        array.sort(new DeviceComparator(array));
        int j = 0;
        while (j < 1) {
            Date time = array.get(0).getSwitchTime(); // saves the first devices switch time
            int same = 1;
            for (int i = 1; i < array.size(); i++) { // counts the devices that has the same switch time
                if (array.get(i).getSwitchTime() != null) {
                    if (array.get(i).getSwitchTime().equals(time)) {
                        same++;

                    }
                }
            }

            if (array.get(j).getSwitchTime() != null && (array.get(j).getSwitchTime().before(initialTime) || array.get(j).getSwitchTime().equals(initialTime))) {
                if (same > 1) {
                    for (int k = 0; k < same; k++) { // sets the devices with the same switch time to null simultaneously
                        array.get(k).setSwitchTime("null", array);
                        array.get(k).switchOnOff();
                    }
                    array.sort(new DeviceComparator(array));
                } else {
                    array.get(j).setSwitchTime("null", array);
                    array.get(j).switchOnOff();
                    array.sort(new DeviceComparator(array));
                }
            } else {
                j++;
            }


        }
    }

    /**
     * This method sets the time to a given date string and updates the devices' statuses accordingly.
     *
     * @param s       a string representing the new time in the format "yyyy-MM-dd_HH:mm:ss".
     * @param devices an ArrayList of Device objects to be updated based on the new time.
     * @throws ParseException           if the input date string is not in the correct format.
     * @throws SmartHomeSystemException if the input date is before the initial time or if there is an error updating the devices.
     * @throws IOException              if there is an error writing to a file.
     */
    public static void setTime(String s, ArrayList<Device> devices) throws ParseException, SmartHomeSystemException, IOException {
        if (CheckAndLook.checkDate(s)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            if (formatter.parse(s).before(initialTime)) {
                throw new SmartHomeSystemException("Time cannot be reversed!");
            } else if (formatter.parse(s).equals(initialTime)) {
                throw new SmartHomeSystemException("There is nothing to change!");
            } else {
                initialTime = formatter.parse(s);
                deviceSetter(devices);
            }
        } else {
            throw new SmartHomeSystemException("Time format is not correct!");
        }
    }


    /**
     * This method skips the specified number of minutes and updates the time in the system. It also updates the device status accordingly.
     *
     * @param elements an array of strings that contains the command and the number of minutes to skip.
     * @param devices  an ArrayList of Device objects that represents the devices in the system.
     * @throws ParseException           if the format of the date is incorrect.
     * @throws IOException              if there is an error while writing to the file.
     * @throws SmartHomeSystemException if the command is erroneous, or the time is reversed.
     */
    public static void skipMinutes(String[] elements, ArrayList<Device> devices) throws ParseException, IOException, SmartHomeSystemException {
        long currentTime = Time.initialTime.getTime();
        if (elements.length > 2) {
            throw new SmartHomeSystemException("Erroneous command!");
        } else {
            try {
                long minute = Long.parseLong(elements[1]);
                if (minute == 0) {
                    throw new SmartHomeSystemException("There is nothing to skip!");
                } else if (minute < 0) {
                    throw new SmartHomeSystemException("Time cannot be reversed!");
                } else {
                    Time.initialTime = new Date(currentTime + Long.parseLong(elements[1]) * 1000 * 60);
                    deviceSetter(devices);
                }
            } catch (NumberFormatException e) {
                throw new SmartHomeSystemException("Erroneous command!");
            }
        }
    }

    /**
     * This method executes the nop command which advances the time to the switch time of the next device to be switched
     * on or off. If there is no device to be switched, it prints an error message.
     *
     * @param devices the list of devices to be considered
     * @throws ParseException           if there is an error in parsing the date
     * @throws IOException              if there is an I/O error while writing to file
     * @throws SmartHomeSystemException if there is an error in the input or execution of the command
     */
    public static void nop(ArrayList<Device> devices) throws ParseException, IOException, SmartHomeSystemException {
        devices.sort(new DeviceComparator(devices));
        int i = 0;
        for (Device d : devices) {
            if (d.getSwitchTime() == null) {
                i++;
            }
        }

        if (i == devices.size()) {
            throw new SmartHomeSystemException("There is nothing to switch!");
        } else {
            for (Device d : devices) {
                if (d.getSwitchTime().after(initialTime)) {
                    Time.initialTime = d.getSwitchTime();
                    break;
                }
            }
        }
        deviceSetter(devices);


    }

}
