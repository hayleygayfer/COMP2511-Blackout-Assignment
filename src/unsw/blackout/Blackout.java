package unsw.blackout;

import java.time.LocalTime;

import org.json.JSONObject;
import org.json.JSONArray;

public class Blackout {
    public void createDevice(String id, String type, double position) {
        if (type == "DesktopDevice") {
            DesktopDevice newDesktopDevice = new DesktopDevice(id, position);
        } else if (type == "HandheldDevice") {
            HandheldDevice newHandheldDevice = new HandheldDevice(id, position);
        } else if (type == "LaptopDevice") {
            LaptopDevice newLaptopDevice = new LaptopDevice(id, position);
        }
    }

    public void createSatellite(String id, String type, double height, double position) {
        if (type == "NasaSatellite") {
            NasaSatellite newNasaSatellite = new NasaSatellite(id, height, position);
        } else if (type == "SovietSatellite") {
            SovietSatellite newSovietSatellite = new SovietSatellite(id, height, position);
        } else if (type == "SpaceXSatellite") {
            SpaceXSatellite newSpaceXSatellite = new SpaceXSatellite(id, height, position);
        } else if (type == "BlueOriginSatellite") {
            BlueOriginSatellite newBlueOriginSatellite = new BlueOriginSatellite(id, height, position);
        }
    }

    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        ActivationPeriod newActivationPeriod = new ActivationPeriod(start, durationInMinutes);
    }

    public void removeSatellite(String id) {
        //TODO
    }

    public void removeDevice(String id) {
        //TODO
    }

    public void moveDevice(String id, double newPos) {
        //TODO
    }

    public JSONObject showWorldState() {
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

    public void simulate(int tickDurationInMinutes) {
        // TODO:
    }
}
