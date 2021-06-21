package unsw.blackout;

import org.json.JSONObject;

import java.time.LocalTime;

public class BlueOriginSatellite extends Satellite {
    private int maxTotalDevices;

    public BlueOriginSatellite(String id, double height, double position) {
        super(id, height, position);
        super.addSupportedDevice("DesktopDevice", 2);
        super.addSupportedDevice("HandheldDevice", Double.POSITIVE_INFINITY);
        super.addSupportedDevice("LaptopDevice", 5);
        super.setConnectionTime(0);
        super.setVelocity(141.66);
        this.maxTotalDevices = 10;
    }

    @Override
    /**
     * Overridden super method to connect to a device that also checks if the number of connections is below
     * the maximum for this Satellite
     */
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        } 
    }

    @Override
    /**
     * @return a JSON object containing this satellite's data, including it's type
     */
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "BlueOriginSatellite");
        return satellite;
    }
}
