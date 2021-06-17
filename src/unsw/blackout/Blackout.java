package unsw.blackout;

import java.time.LocalTime;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;

public class Blackout {
    private ArrayList<Device> devices;
    private ArrayList<Satellite> satellites;

    public Blackout() {
        devices = new ArrayList<Device>();
        satellites = new ArrayList<Satellite>();
    }

    public void createDevice(String id, String type, double position) {
        if (type == "DesktopDevice") {
            DesktopDevice newDesktopDevice = new DesktopDevice(id, position);
            devices.add(newDesktopDevice);
        } else if (type == "HandheldDevice") {
            HandheldDevice newHandheldDevice = new HandheldDevice(id, position);
            devices.add(newHandheldDevice);
        } else if (type == "LaptopDevice") {
            LaptopDevice newLaptopDevice = new LaptopDevice(id, position);
            devices.add(newLaptopDevice);
        }

    }

    public void createSatellite(String id, String type, double height, double position) {
        if (type == "NasaSatellite") {
            NasaSatellite newNasaSatellite = new NasaSatellite(id, height, position);
            satellites.add(newNasaSatellite);
        } else if (type == "SovietSatellite") {
            SovietSatellite newSovietSatellite = new SovietSatellite(id, height, position);
            satellites.add(newSovietSatellite);
        } else if (type == "SpaceXSatellite") {
            SpaceXSatellite newSpaceXSatellite = new SpaceXSatellite(id, height, position);
            satellites.add(newSpaceXSatellite);
        } else if (type == "BlueOriginSatellite") {
            BlueOriginSatellite newBlueOriginSatellite = new BlueOriginSatellite(id, height, position);
            satellites.add(newBlueOriginSatellite);
        }
    }

    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        ActivationPeriod newActivationPeriod = new ActivationPeriod(start, durationInMinutes);
        for (Device device : devices) {
            if (device.getId() == deviceId) {
                device.addActivationPeriod(newActivationPeriod);
            }
        }
    }

    public void removeSatellite(String id) {
        for (Satellite satellite : satellites) {
            if (satellite.getId() == id) {
                satellites.remove(satellite);
            }
        }
    }

    public void removeDevice(String id) {
        for (Device device : devices) {
            if (device.getId() == id) {
                devices.remove(device);
            }
        }
    }

    public void moveDevice(String id, double newPos) {
        for (Device device : devices) {
            if (device.getId() == id) {
                device.setPosition(newPos);
            }
        }
    }

    public JSONObject showWorldState() {
        JSONObject result = new JSONObject();
        JSONArray devices = new JSONArray();
        JSONArray satellites = new JSONArray();

        for (Device device : this.devices) {
            JSONObject JSONDevice = device.createJSON();
            devices.put(JSONDevice);
        }

        for (Satellite satellite : this.satellites) {
            JSONObject JSONSatellite = satellite.createJSON();
            satellites.put(JSONSatellite);
        }

        result.put("devices", devices);
        result.put("satellites", satellites);

        System.out.println(devices.toString());
        System.out.println(satellites.toString());


        // TODO: you'll want to replace this for Task2
        result.put("currentTime", LocalTime.of(0, 0));

        return result;

    }

    public void simulate(int tickDurationInMinutes) {
        // TODO:
    }
}
