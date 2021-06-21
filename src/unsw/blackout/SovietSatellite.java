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
    /**
     * @param newDeviceConnection
     * @param time
     * 
     * overridden method that takes into account maximum connections allowed when connecting to a new device
     */
    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (super.getNumConnections() < maxTotalDevices) {
            super.connectToDevice(newDeviceConnection, time);
        } else {
            super.closeConnection(super.getConnectedDevices().get(0), time);
            super.connectToDevice(newDeviceConnection, time);
        }
    }

    @Override 
    /**
     * Override the super moveSatellite method in order to account for the ability to reverse velocity to stay
     * within [140, 190]
     */
    public void moveSatellite() {
        super.setPosition(super.getPosition() + (this.getVelocity() / super.getHeight()) % 360.0);
        if (super.getPosition() > 190) {
            super.setVelocity(-super.getVelocity());
        } else if (super.getPosition() < 140) {
            super.setVelocity(-super.getVelocity());
        }
    }

    @Override
    /**
     * @return JSON object containing satellite data and type
     */
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "SovietSatellite");
        return satellite;
    }
}
