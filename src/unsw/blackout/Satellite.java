package unsw.blackout;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;

import java.time.LocalTime;

public class Satellite {
    // General fields
    private double height;
    private String id;
    private double position;
    private ArrayList<Device> possibleConnections;
    private ArrayList<Connection> connections;

    // specialised fields
    private double velocity;
    private ArrayList<SupportedDevice> supportedDevices;
    private int connectionTimeInMinutes;

    /**
     * 
     * @param height
     * @param id
     * @param position
     */
    public Satellite(String id, double height, double position) {
        this.height = height;
        this.id = id;
        this.position = position;
        this.possibleConnections = new ArrayList<Device>();
        this.connections = new ArrayList<Connection>();
        this.velocity = 0;
        this.supportedDevices = new ArrayList<SupportedDevice>();
    }

    public String getId() {
        return id;
    }

    public void clearConnectableDevices() {
        possibleConnections.clear();
    }

    public boolean checkDeviceConnectability(Device device) {
        if (MathsHelper.satelliteIsVisibleFromDevice(position, height, device.getPosition())) {
            if (!possibleConnections.contains(device)) {
                possibleConnections.add(device);
            }
            return true;
        }
        return false;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getHeight() {
        return height;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void moveSatellite() {
        position = (position + (this.velocity / this.height)) % 360.0;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getPosition() {
        return this.position;
    }

    public void setConnectionTime(int minutes) {
        this.connectionTimeInMinutes = minutes;
    }

    public int getConnectionTime() {
        return connectionTimeInMinutes;
    }

    public void addSupportedDevice(String type, double maxConnections) {
        SupportedDevice newSupportedDevice = new SupportedDevice(type, maxConnections);
        supportedDevices.add(newSupportedDevice);
    }

    public ArrayList<SupportedDevice> getSupportedDevices() {
        return this.supportedDevices;
    }

    public int getNumConnections() {
        int connectionCount = 0;
        for (Connection connection : this.connections) {
            if (connection.isActive()) {
                connectionCount++;
            }
        }
        return connectionCount;
    }

    public ArrayList<Device> getConnectedDevices() {
        ArrayList<Device> connectedDevices = new ArrayList<Device>();
        for (Connection connection : this.connections) {
            if (connection.isActive()) {
                connectedDevices.add(connection.getConnectedDevice());
            }
        }
        return connectedDevices;
    }

    public void connectToDevice(Device newDeviceConnection, LocalTime time) {
        if (this.getConnectedDevices().contains(newDeviceConnection)) {
            return;
        }

        for (SupportedDevice device : supportedDevices) {
            // see if the device type is supported
            if (newDeviceConnection.getType().equals(device.getType())) {
                // get the number of devices of that type already connected
                int numDeviceTypeConnections = 0;
                for (Connection connection : this.connections) {
                    if (newDeviceConnection.getType().equals(connection.getDeviceType()) && connection.isActive()) {
                        numDeviceTypeConnections++;
                    }
                }
                // check if there is enough space for a new connection
                if (numDeviceTypeConnections < device.getMaxConnections()) {
                    Connection newConnection = new Connection(newDeviceConnection, this, time);
                    connections.add(newConnection);
                    newDeviceConnection.setConnection(true);
                }
            }
        }
    }

    public void closeConnection(Device device, LocalTime time) {
        for (Connection connection : this.connections) {
            if (connection.getConnectedDevice().equals(device) && connection.isActive()) {
                device.setConnection(false);
                connection.endConnection(time);
                return;
            }
        }
    }

    public void clearConnections() {
        for (Connection connection : this.connections) {
            connections.remove(connection);
            connection.getConnectedDevice().setConnection(false);
        }
    }

    public JSONObject createJSON() {
        JSONObject satellite = new JSONObject();
        JSONArray connections = new JSONArray();
        JSONArray possibleConnections = new JSONArray();

        for (Connection connection : this.connections) {
            JSONObject JSONConnection = connection.createJSON();
            connections.put(JSONConnection);
        }

        for (Device device : this.possibleConnections) {
            possibleConnections.put(device.getId());
        }

        satellite.put("connections", connections);
        satellite.put("height", height);
        satellite.put("id", id);
        satellite.put("velocity", this.getVelocity());
        satellite.put("position", Math.round(position * 100.0) / 100.0);
        satellite.put("possibleConnections", possibleConnections);

        return satellite;
    }
}


