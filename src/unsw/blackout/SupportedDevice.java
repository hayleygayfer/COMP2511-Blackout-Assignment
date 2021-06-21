package unsw.blackout;

public class SupportedDevice {
    private String type;
    private double maxConnections;

    public SupportedDevice(String type, double maxConnections) {
        this.type = type;
        this.maxConnections = maxConnections;
    }

    /**
     * 
     * @return the type of the Supported Device
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @return the maximum allowed connections for the supported device
     */
    public double getMaxConnections() {
        return maxConnections;
    }
}
