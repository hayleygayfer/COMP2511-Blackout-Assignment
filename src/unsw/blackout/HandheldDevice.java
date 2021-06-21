package unsw.blackout;

import org.json.JSONObject;

public class HandheldDevice extends Device {
    public HandheldDevice(String id, double position) {
        super(id, position);
        super.setConnectionTime(1);
    }

    @Override
    public String getType() {
        return "HandheldDevice";
    }

    @Override
    public JSONObject createJSON() {
        return super.createJSON().put("type", "HandheldDevice");
    }
}
