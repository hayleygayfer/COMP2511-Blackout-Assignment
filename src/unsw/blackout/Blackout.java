package unsw.blackout;

import java.time.LocalTime;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.*;

public class Blackout {
    private ArrayList<Device> devices = new ArrayList<Device>();
    private ArrayList<Satellite> satellites = new ArrayList<Satellite>();
    private LocalTime currentTime = LocalTime.of(0,0);

    /**
     * 
     * @param id
     * @param type
     * @param position
     */
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

    /**
     * 
     * @param id
     * @param type
     * @param height
     * @param position
     */
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

    /**
     * 
     * @param deviceId
     * @param start
     * @param durationInMinutes
     */
    public void scheduleDeviceActivation(String deviceId, LocalTime start, int durationInMinutes) {
        ActivationPeriod newActivationPeriod = new ActivationPeriod(start, durationInMinutes);
        for (Device device : devices) {
            if (device.getId().equals(deviceId)) {
                device.addActivationPeriod(newActivationPeriod);
            }
        }
    }

    /**
     * 
     * @param id
     */
    public void removeSatellite(String id) {
        for (Satellite satellite : satellites) {
            if (satellite.getId().equals(id)) {
                satellites.remove(satellite);
            }
        }
    }

    /**
     * 
     * @param id
     */
    public void removeDevice(String id) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                devices.remove(device);
            }
        }
    }

    /**
     * 
     * @param id
     * @param newPos
     */
    public void moveDevice(String id, double newPos) {
        for (Device device : devices) {
            if (device.getId().equals(id)) {
                device.setPosition(newPos);
            }
        }
    }

    /**
     * 
     * @return JSON object containing the WorldState
     */
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

        this.devices.sort(Comparator.comparing(Device::getId));

        for (Device device : this.devices) {
            JSONObject JSONDevice = device.createJSON();
            devices.put(JSONDevice);
        }

        this.satellites.sort(Comparator.comparing(Satellite::getId));

        for (Satellite satellite : this.satellites) {
            JSONObject JSONSatellite = satellite.createJSON();
            satellites.put(JSONSatellite);
        }

        result.put("devices", devices);
        result.put("satellites", satellites);

        result.put("currentTime", currentTime);

        return result;

    }

    /**
     * 
     * @param tickDurationInMinutes
     * 
     * Simulate the movement of Satellites and their connections to Devices over a period of time in minutes
     */
    public void simulate(int tickDurationInMinutes) {
        this.devices.sort(Comparator.comparing(Device::getId));
        this.satellites.sort(Comparator.comparing(Satellite::getPosition));

        for (int i = 0; i < tickDurationInMinutes; i++) {
            for (Satellite satellite : this.satellites) {
                // move satellite and clear connectable devices
                satellite.moveSatellite();
                
                // disconnect all devices that are no longer activated or leave range
                for (Device device : satellite.getConnectedDevices()) {
                    if (!device.isActivated(currentTime) || !satellite.checkDeviceConnectability(device)) {
                        satellite.closeConnection(device, currentTime);
                    }
                }

                for (Device device : this.devices) {
                    if (satellite.checkDeviceConnectability(device) && 
                        device.isActivated(currentTime) && 
                        !satellite.getConnectedDevices().contains(device) &&
                        !device.isConnected()) {
                        satellite.connectToDevice(device, currentTime);
                    }
                }
            }

            currentTime = currentTime.plusMinutes(1);
        }
    }
}
