package unsw.blackout;

import java.util.ArrayList;

public class Device {
    private String id;
    private double position;
    private boolean isConnected;
    // make this more specific later
    private ArrayList<ActivationPeriod> activationPeriods;

    public Device() {
        this.id = "Device";
        this.position = 10;
        this.activationPeriods = new ArrayList<ActivationPeriod>();
    }

    public Device(String id, double position) {
        this.id = id;
        this.position = position;
        this.activationPeriods = new ArrayList<ActivationPeriod>();
    }

    // SETTERS

    public void setId(String id) {
        this.id = id;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void addActivationPeriod(ActivationPeriod activationPeriod) {
        activationPeriods.add(activationPeriod);
    }

    public void removeActivationPeriod(ActivationPeriod activationPeriod) {
        activationPeriods.remove(activationPeriod);
    }

    public void clearActivationPeriods() {
        activationPeriods.clear();
    }

    // GETTERS

    public String getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
