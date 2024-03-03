import java.util.ArrayList;

/**
 * This class provides methods for checking the format of a date string and looking up the index of a device with a given name in a list of devices.
 */
public class CheckAndLook {

    /**
     * Checks if a given date string has a valid format in the "yyyy-MM-dd_HH:mm:ss" format.
     *
     * @param s the date string to be checked
     * @return true if the date string has a valid format, false otherwise
     */
    public static boolean checkDate(String s) {
        String[] l = s.split("_");
        String[] yearMonthDay = l[0].split("-");
        String[] hourMinuteSecond = l[1].split(":");
        int month = Integer.parseInt(yearMonthDay[1]);
        int day = Integer.parseInt(yearMonthDay[2]);
        int hours = Integer.parseInt(hourMinuteSecond[0]);
        int minutes = Integer.parseInt(hourMinuteSecond[1]);
        int seconds = Integer.parseInt(hourMinuteSecond[2]);
        if (month < 1 || month > 12) {
            return false;
        } else if (day < 1 || day > 31) {
            return false;
        } else if (hours < 0 || hours > 23) {
            return false;
        } else if (minutes < 0 || minutes > 59) {
            return false;
        } else return seconds >= 0 && seconds <= 59;
    }

    /**
     * Looks up the index of a device with a given name in the list of devices.
     *
     * @param deviceName the name of the device to look up
     * @return the index of the device in the list, or -1 if the device is not found
     */
    public static int lookupDevice(String deviceName, ArrayList<Device> devices) {
        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getName().equals(deviceName)) {
                return i;
            }
        }
        return -1;
    }
}
