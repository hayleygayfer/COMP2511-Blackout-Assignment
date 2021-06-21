package unsw.blackout;

import org.json.JSONObject;

public class LaptopDevice extends Device {
    public LaptopDevice(String id, double position) {
        super(id, position);
        super.setConnectionTime(2);
    }

    @Override
    /**
     * @return the type of device
     */
    public String getType() {
        return "LaptopDevice";
    }

    @Override
    /**
     * @return a JSON object containing device data and the type
     */
    public JSONObject createJSON() {
        return super.createJSON().put("type", "LaptopDevice");
    }
}
