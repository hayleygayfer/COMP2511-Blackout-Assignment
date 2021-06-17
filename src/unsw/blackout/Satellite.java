package unsw.blackout;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;

public class Satellite {
    // General fields
    private double height;
    private String id;
    private double position;
    private ArrayList<Device> possibleConnections;
    private ArrayList<Connection> connections;

    // Constructor with no arguments
    public Satellite() {
        this.height = 10000;
        this.id = "Satellite";
        this.position = 10;
    }

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
    }

    public String getId() {
        return id;
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
        satellite.put("position", position);
        satellite.put("possibleConnections", possibleConnections);

        return satellite;
    }

}


