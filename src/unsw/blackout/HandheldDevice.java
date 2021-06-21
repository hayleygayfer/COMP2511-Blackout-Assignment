package unsw.blackout;

import org.json.JSONObject;

public class HandheldDevice extends Device {
    public HandheldDevice(String id, double position) {
        super(id, position);
        super.setConnectionTime(1);
    }

    @Override
    /**
     * @return the type of device
     */
    public String getType() {
        return "HandheldDevice";
    }

    @Override
    /**
     * @return a JSON object containing the device data and the type
     */
    public JSONObject createJSON() {
        return super.createJSON().put("type", "HandheldDevice");
    }
}
