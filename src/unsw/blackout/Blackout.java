package unsw.blackout;

import java.time.LocalTime;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;

public class Blackout {
    private ArrayList<Device> devices = new ArrayList<Device>();
    private ArrayList<Satellite> satellites = new ArrayList<Satellite>();
    private LocalTime currentTime = LocalTime.of(0,0);

    public void createDevice(String id, String type, double position) {
        if (type.equals("DesktopDevice")) {
            DesktopDevice newDesktopDevice = new DesktopDevice(id, position);
            devices.add(newDesktopDevice);
        } else if (type.equals("HandheldDevice")) {
            HandheldDevice newHandheldDevice = new HandheldDevice(id, position);
            devices.add(newHandheldDevice);
        } else if (type.equals("LaptopDevice")) {
            LaptopDevice newLaptopDevice = new LaptopDevice(id, position);
            devices.add(newLaptopDevice);
        }
    }

    public void createSatellite(String id, String type, double height, double position) {
        if (type.equals("NasaSatellite")) {
            NasaSatellite newNasaSatellite = new NasaSatellite(id, height, position);
            satellites.add(newNasaSatellite);
        } else if (type.equals("SovietSatellite")) {
            SovietSatellite newSovietSatellite = new SovietSatellite(id, height, position);
            satellites.add(newSovietSatellite);
        } else if (type.equals("SpaceXSatellite")) {
            SpaceXSatellite newSpaceXSatellite = new SpaceXSatellite(id, height, position);
            satellites.add(newSpaceXSatellite);
        } else if (type.equals("BlueOriginSatellite")) {
            BlueOriginSatellite newBlueOriginSatellite = new BlueOriginSatellite(id, height, position);
            satellites.add(newBlueOriginSatellite);
        }
    }

    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        ActivationPeriod newActivationPeriod = new ActivationPeriod(start, durationInMinutes);
        for (Device device : devices) {
            if (device.getId().equals(deviceId)) {
                device.addActivationPeriod(newActivationPeriod);
            }
        }
    }

    public void removeSatellite(String id) {
        for (Satellite satellite : satellites) {
            if (satellite.getId().equals(id)) {
                satellites.remove(satellite);
            }
        }
    }

    public void removeDevice(String id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                devices.remove(device);
            }
        }
    }

    public void moveDevice(String id, double newPos) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                device.setPosition(newPos);
            }
        }
    }

    public JSONObject showWorldState() {
        // update possible connections
        for (Satellite satellite : this.satellites) {
            satellite.clearConnectableDevices();
            for (Device device : this.devices) {
                satellite.checkDeviceConnectability(device);
            }
        }

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

        result.put("currentTime", currentTime);

        return result;

    }

    public void simulate(int tickDurationInMinutes) {
        for (int i = 0; i < tickDurationInMinutes; i++) {
            currentTime.plusMinutes(1);

            for (Satellite satellite : this.satellites) {
                satellite.moveSatellite();
                satellite.clearConnectableDevices();
                satellite.clearConnections();
                for (Device device : this.devices) {
                    if (satellite.checkDeviceConnectability(device)) satellite.connectToDevice(device, currentTime);
                }
            }
        }
    }
}
