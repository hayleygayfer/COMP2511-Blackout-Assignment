package unsw.blackout;

import org.json.JSONObject;

public class SovietSatellite extends Satellite {
    public SovietSatellite(String id, double height, double position) {
        super(id, height, position);
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "SovietSatellite");
        return satellite;
    }
}
