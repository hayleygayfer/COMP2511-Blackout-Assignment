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
        super.setVelocity(5100.0 / 60.0);
        this.maxTotalDevices = 6;
    }

    @Override
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        } else if (30 <= newDeviceConnection.getPosition() && newDeviceConnection.getPosition() <= 40) {
            for (Device device : super.getConnectedDevices()) {
                if (device.getPosition() > 40 || device.getPosition() < 30) {
                    super.removeConnection(device);
                    super.connectToDevice(newDeviceConnection, time);
                }
            }
        }
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "NasaSatellite");
        return satellite;
    }
}
