package unsw.blackout;

import org.json.JSONObject;

public class NasaSatellite extends Satellite {
    public NasaSatellite(String id, double height, double position) {
        super(id, height, position);
    }

    @Override
    public JSONObject createJSON() {
        JSONObject satellite = super.createJSON();
        satellite.put("type", "NasaSatellite");
        return satellite;
    }
}
