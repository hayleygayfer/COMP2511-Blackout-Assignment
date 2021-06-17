package unsw.blackout;

import org.json.JSONObject;

public class BlueOriginSatellite extends Satellite {
    public BlueOriginSatellite(String id, double height, double position) {
        super(id, height, position);
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "BlueOriginSatellite");
        return satellite;
    }
}
