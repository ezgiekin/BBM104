import java.util.ArrayList;
import java.util.Comparator;

public class DeviceComparator implements Comparator<Device> {

    private final ArrayList<Device> devices;

    /**
     * Constructs a DeviceComparator object with the given ArrayList of Device objects.
     *
     * @param devices the ArrayList of Device objects
     */
    public DeviceComparator(ArrayList<Device> devices) {
        this.devices = devices;
    }

    /**
     * Compares two devices based on their switch time in ascending order.
     * If both devices have no switch time, returns 0.
     * If d1 has no switch time and d2 has, returns 1.
     * If d2 has no switch time and d1 has, returns -1.
     * If both devices have the same switch time or no switch time, compares their position in the device list.
     *
     * @param d1 the first device to be compared
     * @param d2 the second device to be compared
     * @return an integer indicating the comparison result
     */
    public int compare(Device d1, Device d2) {
        if (d1.getSwitchTime() != null && d2.getSwitchTime() != null) {
            int switchTime = d1.getSwitchTime().compareTo(d2.getSwitchTime());
            if (switchTime != 0) {
                return switchTime;
            }
        } else if (d1.getSwitchTime() == null && d2.getSwitchTime() == null) {
            return 0;
        } else if (d1.getSwitchTime() == null) {
            return 1;
        } else {
            return -1;
        }
        return Integer.compare(devices.indexOf(d1), devices.indexOf(d2));
    }
}
