package unsw.blackout;

import org.json.JSONObject;

import java.time.LocalTime;

public class SovietSatellite extends Satellite {
    private int maxTotalDevices;

    public SovietSatellite(String id, double height, double position) {
        super(id, height, position);
        super.setOrbitSpeed(6000);
        super.addSupportedDevice("LaptopDevice", Double.POSITIVE_INFINITY);
        super.addSupportedDevice("DesktopDevice", Double.POSITIVE_INFINITY);
    }

    @Override
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        } else {
            super.removeConnection(super.getConnectedDevices().get(0));
            super.connectToDevice(newDeviceConnection, time);
        }
    }



    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "SovietSatellite");
        return satellite;
    }
}
