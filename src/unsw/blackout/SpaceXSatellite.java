package unsw.blackout;

import org.json.JSONObject;

public class SpaceXSatellite extends Satellite {
    public SpaceXSatellite(String id, double height, double position) {
        super(id, height, position);
        super.addSupportedDevice("HandheldDevice", Double.POSITIVE_INFINITY);
        super.setConnectionTime(0);
        super.setVelocity(55.5);
    }

    @Override
    /**
     * @return JSON object containing satellite data and type
     */
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "SpaceXSatellite");
        return satellite;
    }
}
