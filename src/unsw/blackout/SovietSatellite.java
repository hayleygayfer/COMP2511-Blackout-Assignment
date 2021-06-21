package unsw.blackout;

import org.json.JSONObject;

import java.time.LocalTime;

public class SovietSatellite extends Satellite {
    private int maxTotalDevices;

    public SovietSatellite(String id, double height, double position) {
        super(id, height, position);
        super.setVelocity(100);
        super.addSupportedDevice("LaptopDevice", Double.POSITIVE_INFINITY);
        super.addSupportedDevice("DesktopDevice", Double.POSITIVE_INFINITY);
        this.maxTotalDevices = 9;
    }

    @Override
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        } else {
            super.closeConnection(super.getConnectedDevices().get(0), time);
            super.connectToDevice(newDeviceConnection, time);
        }
    }

    @Override 
    public void moveSatellite() {
        super.setPosition(super.getPosition() + (this.getVelocity() / super.getHeight()) % 360.0);
        if (super.getPosition() > 190) {
            super.setVelocity(-super.getVelocity());
        } else if (super.getPosition() < 140) {
            super.setVelocity(-super.getVelocity());
        }
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "SovietSatellite");
        return satellite;
    }
}
