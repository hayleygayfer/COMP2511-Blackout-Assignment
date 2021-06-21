package unsw.blackout;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.LocalTime;

public class Device {
    private String id;
    private double position;
    private boolean isConnected;
    // make this more specific later
    private ArrayList<ActivationPeriod> activationPeriods;
    private int connectionTimeInMinutes;

    public Device(String id, double position) {
        this.id = id;
        this.position = position;
        this.activationPeriods = new ArrayList<ActivationPeriod>();
        this.isConnected = false;
    }

    /**
     * 
     * @param connectionTimeInMinutes
     * 
     * Set the connectiontime of the device (used in the children's constructors)
     */
    public void setConnectionTime(int connectionTimeInMinutes) {
        this.connectionTimeInMinutes = connectionTimeInMinutes;
    }

    /**
     * 
     * @return Boolean if the device is currently connected to a satellite
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * 
     * @return The connection time of the device
     * 
     * Used to calculate the duration of Connections
     */
    public int getConnectionTime() {
        return connectionTimeInMinutes;
    }

    /**
     * 
     * @return The id of the device
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return the position of the device
     */
    public double getPosition() {
        return position;
    }

    /**
     * 
     * @return a string containing the type
     * 
     * This method is overridden in children classes
     */
    public String getType() {
        return "";
    }

    /**
     * 
     * @param newPosition
     * 
     * Set a new position for the device
     */
    public void setPosition(double newPosition) {
        this.position = newPosition;
    }

    /**
     * 
     * @param newActivationPeriod
     * 
     * Add an additional activation period to the device
     */
    public void addActivationPeriod(ActivationPeriod newActivationPeriod) {
        activationPeriods.add(newActivationPeriod);
    }

    /**
     * 
     * @param time
     * @return Boolean if the device is currently active
     * 
     * The time given must be within one of the device's activation periods
     */
    public boolean isActivated(LocalTime time) {
        for (ActivationPeriod period : activationPeriods) {
            if (period.isDuring(time)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param isConnected
     * 
     * Set to true or false if device is currently connected to a satellite
     */
    public void setConnection(boolean isConnected) {
        this.isConnected = isConnected;
    }

    /**
     * 
     * @return a JSON object containing the device data
     */
    public JSONObject createJSON() {
        JSONObject device = new JSONObject();
        JSONArray activationPeriods = new JSONArray();
        
        for (ActivationPeriod activationPeriod : this.activationPeriods) {
            JSONObject JSONActivationPeriod = activationPeriod.createJSON();
            activationPeriods.put(JSONActivationPeriod);
        }

        device.put("activationPeriods", activationPeriods);
        device.put("id", id);
        device.put("isConnected", isConnected);
        device.put("position", position);

        return device;
    }
}
