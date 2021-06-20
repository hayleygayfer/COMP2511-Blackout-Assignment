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
        super.setVelocity(8500.0 / 60.0);
        this.maxTotalDevices = 10;
    }

    @Override
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        } 
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "BlueOriginSatellite");
        return satellite;
    }
}
