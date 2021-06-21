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

    /**
     * 
     * @return a String containing the Satellite id
     */
    public String getId() {
        return id;
    }

    /**
     * Clear all connectable devices (Used in blackout to 'refresh' the satellite's potential connections)
     */
    public void clearConnectableDevices() {
        possibleConnections.clear();
    }

    /**
     * 
     * @param device
     * @return a boolean determining whether a device is visible from the satellite or not
     * 
     * If a device is visible and is not currently contained within the possibleConnections, it adds it
     */
    public boolean checkDeviceConnectability(Device device) {
        if (MathsHelper.satelliteIsVisibleFromDevice(position, height, device.getPosition())) {
            if (!possibleConnections.contains(device)) {
                possibleConnections.add(device);
            }
            return true;
        }
        return false;
    }

    /**
     * 
     * @return the velocity of the satellite
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * @return the height of the satellite
     */
    public double getHeight() {
        return height;
    }

    /**
     * 
     * @param velocity
     * 
     * Set the velocity
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Move the satellite by altering the position according to the velocity and height, and ensure
     * the position wraps around to 0 from 360
     */
    public void moveSatellite() {
        position = (position + (this.velocity / this.height)) % 360.0;
    }

    /**
     * 
     * @param position
     * 
     * Set the position
     */
    public void setPosition(double position) {
        this.position = position;
    }

    /**
     * 
     * @return the position of the satellite
     */
    public double getPosition() {
        return this.position;
    }

    /**
     * 
     * @param minutes
     * 
     * Get the connectionTime of the Satellite in minutes, this is relevant to the children classes
     */
    public void setConnectionTime(int minutes) {
        this.connectionTimeInMinutes = minutes;
    }

    /**
     * 
     * @return the connection time in minutes
     */
    public int getConnectionTime() {
        return connectionTimeInMinutes;
    }

    /**
     * 
     * @param type
     * @param maxConnections
     * 
     * Add a supportedDevice to the list of supported devices for the satellite, containing the type and max connections
     * Relevant for the children classes e.g. BlueOriginSatellite > addSupportedDevice("Desktop", 2)
     */
    public void addSupportedDevice(String type, double maxConnections) {
        SupportedDevice newSupportedDevice = new SupportedDevice(type, maxConnections);
        supportedDevices.add(newSupportedDevice);
    }

    /**
     * 
     * @return a list of supported devices
     */
    public ArrayList<SupportedDevice> getSupportedDevices() {
        return this.supportedDevices;
    }

    /**
     * 
     * @return the number of devices the satellite is currently connected to
     * 
     * Used for monitoring that the number of connections for a satellite is below the maximum
     */
    public int getNumConnections() {
        int connectionCount = 0;
        for (Connection connection : this.connections) {
            if (connection.isActive()) {
                connectionCount++;
            }
        }
        return connectionCount;
    }

    /**
     * 
     * @return a list of Device objects containing devices that are currently connected to the satellite
     */
    public ArrayList<Device> getConnectedDevices() {
        ArrayList<Device> connectedDevices = new ArrayList<Device>();
        for (Connection connection : this.connections) {
            if (connection.isActive()) {
                connectedDevices.add(connection.getConnectedDevice());
            }
        }
        return connectedDevices;
    }

    /**
     * 
     * @param newDeviceConnection
     * @param time
     * 
     * Create a new Connection to a new Device started at the given time
     */
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

    /**
     * 
     * @param device
     * @param time
     * 
     * Set a connection to inactive (ended at the given time) and the given device to 'not connected'
     */
    public void closeConnection(Device device, LocalTime time) {
        for (Connection connection : this.connections) {
            if (connection.getConnectedDevice().equals(device) && connection.isActive()) {
                device.setConnection(false);
                connection.endConnection(time);
                return;
            }
        }
    }

    /**
     * clear all current connections
     */
    public void clearConnections() {
        for (Connection connection : this.connections) {
            connections.remove(connection);
            connection.getConnectedDevice().setConnection(false);
        }
    }

    /**
     * 
     * @return a JSON object containing the satellite data
     */
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


