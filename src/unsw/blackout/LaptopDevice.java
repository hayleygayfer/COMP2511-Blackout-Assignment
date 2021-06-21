package unsw.blackout;

import org.json.JSONObject;

public class LaptopDevice extends Device {
    public LaptopDevice(String id, double position) {
        super(id, position);
        super.setConnectionTime(2);
    }

    @Override
    public String getType() {
        return "LaptopDevice";
    }

    @Override
    public JSONObject createJSON() {
        return super.createJSON().put("type", "LaptopDevice");
    }
}
