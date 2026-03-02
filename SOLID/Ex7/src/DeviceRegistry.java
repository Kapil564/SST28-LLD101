import java.util.*;

public class DeviceRegistry {
    private final List<SmartClassroomDevice> devices = new ArrayList<>();

    public void add(SmartClassroomDevice d) { devices.add(d); }

    public <T> T getFirstByCapability(Class<T> capability) {
        for (SmartClassroomDevice d : devices) {
            if (capability.isInstance(d)) {
                return capability.cast(d);
            }
        }
        throw new IllegalStateException("Missing capability: " + capability.getSimpleName());
    }
    public <T> List<T> getAllByCapability(Class<T> capability) {
        List<T> list = new ArrayList<>();
        for (SmartClassroomDevice d : devices) {
            if (capability.isInstance(d)) {
                list.add(capability.cast(d));
            }
        }
        return list;
    }
}