package unsw.blackout;

public class Blackout {
    public void addDevice(String id, String type, double position) {
        // TODO:
    }

    public void addSatellite(String id, String type, double height, double position) {
        // TODO:
    }

    public void addActivationPeriod(String deviceId, LocalTime start, int durationInMinutes) {
        // TODO:
    }

    public void removeSatellite(String id) {
        // TODO:
    }

    public void removeDevice(String id) {
        // TODO:
    }

    public void moveDevice(String id, double newPos) {
        // TODO:
    }

    public JSONObject show() {
        JSONObject result = new JSONObject();
        JSONArray devices = new JSONArray();
        JSONArray satellites = new JSONArray();

        // TODO:

        result.put("devices", devices);
        result.put("satellites", satellites);

        // TODO: you'll want to replace this for Task2
        result.put("currentTime", LocalTime.of(0, 0));

        return result;
    }

    public void tick(int tickDurationInMinutes) {
        // TODO:
    }
}
