package unsw.blackout;

import org.json.JSONObject;

public class SpaceXSatellite extends Satellite {
    public SpaceXSatellite(String id, double height, double position) {
        super(id, height, position);
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "SpaceXSatellite");
        return satellite;
    }
}
