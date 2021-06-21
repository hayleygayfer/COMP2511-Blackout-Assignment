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

    public boolean isActive() {
        return isActive;
    }

    public String getDeviceType() {
        return connectedDevice.getType();
    }

    public Device getConnectedDevice() {
        return connectedDevice;
    }

    public void endConnection(LocalTime endTime) {
        this.isActive = false;
        this.endTime = endTime;
    }

    public int getMinutesActive() {
        Duration duration;
        duration = Duration.between(startTime, endTime); 
        int minutes = (int) (duration.toMinutes()) - connectedSatellite.getConnectionTime() - 1;
        if (connectedSatellite.getClass().equals(BlueOriginSatellite.class)) {
            minutes -= connectedDevice.getConnectionTime();
        } else if (connectedSatellite.getClass().equals(SovietSatellite.class)) {
            minutes -= connectedDevice.getConnectionTime() * 2;
        }

        if (minutes < 0) minutes = 0;
        return minutes;
    }

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
