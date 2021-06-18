package unsw.blackout;

import org.json.JSONObject;

public class DesktopDevice extends Device {
    public DesktopDevice(String id, double position) {
        super(id, position);
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
