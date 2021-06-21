package unsw.blackout;

import org.json.JSONObject;

import java.time.LocalTime;

public class NasaSatellite extends Satellite {
    private int maxTotalDevices;

    public NasaSatellite(String id, double height, double position) {
        super(id, height, position);
        // add all supported devices
        super.addSupportedDevice("DesktopDevice", Double.POSITIVE_INFINITY);
        super.addSupportedDevice("HandheldDevice", Double.POSITIVE_INFINITY);
        super.addSupportedDevice("LaptopDevice", Double.POSITIVE_INFINITY);
        super.setConnectionTime(10);
        super.setVelocity(85);
        this.maxTotalDevices = 6;
    }

    @Override
    /**
     * @param newDeviceConnection
     * @param time
     * 
     * overrides the connectToDevice method, checks if the number of connections is below the max, and if not
     * if the potential Device connection is within the [30, 40] range, in range of the satellite, and there
     * exists another connected Device that is outside of this range
     * 
     */
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        // check if device is within [30,40] range
        } else if (newDeviceConnection.getPosition() >= 30 && newDeviceConnection.getPosition() <= 40) {
            for (Device device : super.getConnectedDevices()) {
                // check if connected device outside of [30,40] range exists
                if (device.getPosition() > 40 || device.getPosition() < 30) {
                    super.closeConnection(device, time);
                    super.connectToDevice(newDeviceConnection, time);
                    return;
                }
            }
        }
    }

    @Override
    /**
     * @return JSON object containing Satellite data and type
     */
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "NasaSatellite");
        return satellite;
    }
}
