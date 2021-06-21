package unsw.blackout;

import org.json.JSONObject;

public class DesktopDevice extends Device {
    public DesktopDevice(String id, double position) {
        super(id, position);
        super.setConnectionTime(5);
    }

    @Override
    /**
     * @return the type of Device "DesktopDevice"
     */
    public String getType() {
        return "DesktopDevice";
    }

    @Override
    /**
     * @return a JSON object containing the Device data plus the type of device
     */
    public JSONObject createJSON() {
        return super.createJSON().put("type", "DesktopDevice");
    }
}
