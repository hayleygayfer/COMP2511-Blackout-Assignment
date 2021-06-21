package unsw.blackout;

import java.time.*;
import org.json.JSONObject;

public class Connection {
    private Satellite connectedSatellite;
    private Device connectedDevice;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isActive;

    public Connection(Device connectedDevice, Satellite connectedSatellite, LocalTime startTime) {
        this.connectedDevice = connectedDevice;
        this.connectedSatellite = connectedSatellite;
        this.startTime = startTime;
        this.endTime = LocalTime.of(0,0);
        this.isActive = true;
    }

    /**
     * 
     * @return True or False depending on if the connection is still currently active, or has been closed
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * 
     * @return The type of device in the connection e.g. "Handheld"
     */
    public String getDeviceType() {
        return connectedDevice.getType();
    }

    /**
     * 
     * @return The Device object of the connected device
     */
    public Device getConnectedDevice() {
        return connectedDevice;
    }

    /**
     * 
     * @param endTime
     * 
     * Set the connection to inactive and set it's endTime to the given time
     */
    public void endConnection(LocalTime endTime) {
        this.isActive = false;
        this.endTime = endTime;
    }

    /**
     * 
     * @return the number of minutes the connection was active, taking into account the connection time of
     * different satellites
     */
    public int getMinutesActive() {
        Duration duration;
        duration = Duration.between(startTime, endTime); 
        int minutes = (int) (duration.toMinutes()) - connectedSatellite.getConnectionTime() - 1;
        if (connectedSatellite.getClass().equals(BlueOriginSatellite.class)) {
            minutes -= connectedDevice.getConnectionTime();
        } else if (connectedSatellite.getClass().equals(SovietSatellite.class)) {
            minutes -= connectedDevice.getConnectionTime() * 2;
        }

        // Ensure no negative times
        if (minutes < 0) minutes = 0;
        return minutes;
    }

    /**
     * 
     * @return A JSON object containing all the Connection data
     */
    public JSONObject createJSON() {
        JSONObject connection = new JSONObject();
        connection.put("deviceId", connectedDevice.getId());
        connection.put("endTime", endTime.toString());
        connection.put("minutesActive", this.getMinutesActive());
        connection.put("satelliteId", connectedSatellite.getId());
        connection.put("startTime", startTime.toString());
        
        return connection;
    }
}
