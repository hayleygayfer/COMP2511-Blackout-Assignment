package unsw.blackout;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class Device {
    private String id;
    private double position;
    private boolean isConnected;
    // make this more specific later
    private ArrayList<ActivationPeriod> activationPeriods;

    public Device(String id, double position) {
        this.id = id;
        this.position = position;
        this.activationPeriods = new ArrayList<ActivationPeriod>();
    }

    public String getId() {
        return id;
    }

    public double getPosition() {
        return position;
    }

    public String getType() {
        return "";
    }

    public void setPosition(double newPosition) {
        this.position = newPosition;
    }

    public void addActivationPeriod(ActivationPeriod newActivationPeriod) {
        activationPeriods.add(newActivationPeriod);
    }

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
