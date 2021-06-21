package unsw.blackout;

import org.json.JSONObject;

public class DesktopDevice extends Device {
    public DesktopDevice(String id, double position) {
        super(id, position);
        super.setConnectionTime(5);
    }

    @Override
    public String getType() {
        return "DesktopDevice";
    }

    @Override
    public JSONObject createJSON() {
        return super.createJSON().put("type", "DesktopDevice");
    }
}
