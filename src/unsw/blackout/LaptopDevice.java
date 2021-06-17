package unsw.blackout;

import org.json.JSONObject;

public class LaptopDevice extends Device {
    public LaptopDevice(String id, double position) {
        super(id, position);
    }

    @Override
    public JSONObject createJSON() {
        return super.createJSON().put("type", "LaptopDevice");
    }
}