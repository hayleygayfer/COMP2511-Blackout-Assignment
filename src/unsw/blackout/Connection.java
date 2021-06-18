package unsw.blackout;

import java.time.*;
import org.json.JSONObject;

public class Connection {
    private Satellite connectedSatellite;
    private Device connectedDevice;
    private LocalTime startTime;
    private LocalTime endTime;

    public Connection(Device connectedDevice, Satellite connectedSatellite) {
        this.connectedDevice = connectedDevice;
        this.connectedSatellite = connectedSatellite;
    }

    public String getDeviceType() {
        return connectedDevice.getType();
    }

    public Device getConnectedDevice() {
        return connectedDevice;
    }

    public int getMinutesActive() {
        Duration duration = Duration.between(startTime, endTime); 
        return (int) duration.toMinutes();
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
