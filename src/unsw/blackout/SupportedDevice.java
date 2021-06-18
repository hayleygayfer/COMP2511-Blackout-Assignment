package unsw.blackout;

public class SupportedDevice {
    private String type;
    private double maxConnections;

    public SupportedDevice(String type, double maxConnections) {
        this.type = type;
        this.maxConnections = maxConnections;
    }

    public String getType() {
        return type;
    }

    public double getMaxConnections() {
        return maxConnections;
    }
}
