package unsw.blackout;

import org.json.JSONObject;

public class BlueOriginSatellite extends Satellite {
    private int maxTotalDevices;

    public BlueOriginSatellite(String id, double height, double position) {
        super(id, height, position);
        super.setOrbitSpeed(8500);
        super.addSupportedDevice("DesktopDevice", 2);
        super.addSupportedDevice("HandheldDevice", Double.POSITIVE_INFINITY);
        super.addSupportedDevice("LaptopDevice", 5);
        super.setConnectionTime(0);
        this.maxTotalDevices = 10;
    }

    @Override
    public void connectToDevice(Device newDeviceConnection) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection);
        } 
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "BlueOriginSatellite");
        return satellite;
    }
}
