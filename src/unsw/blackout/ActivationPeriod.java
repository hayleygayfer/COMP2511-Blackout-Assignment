package unsw.blackout;

import java.time.LocalTime;
import org.json.JSONObject;

public class ActivationPeriod {
    //change these types later
    private LocalTime startTime;
    private int durationInMinutes;

    /**
     * 
     * @param startTime
     * @param durationInMinutes
     */
    public ActivationPeriod(LocalTime startTime, int durationInMinutes) {
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
    }

    /**
     * 
     * @return start time of activation period
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * 
     * @return end time of activation period
     */
    public LocalTime getEndTime() {
        return startTime.plusMinutes(durationInMinutes);
    }

    /**
     * 
     * @return a JSON object containing the activationPeriod data
     */
    public JSONObject createJSON() {
        JSONObject activationPeriod = new JSONObject();
        activationPeriod.put("endTime", this.getEndTime().toString());
        activationPeriod.put("startTime", startTime.toString());
        return activationPeriod;
    }

    /**
     * 
     * @param currentTime
     * @return True or False depending on whether a given time is within the activation period
     */
    public boolean isDuring(LocalTime currentTime) {
        if (currentTime.equals(startTime) || currentTime.equals(getEndTime())) {
            return true;
        } else if (currentTime.isAfter(startTime) && currentTime.isBefore(getEndTime())) {
            return true;
        } else {
            return false;
        }
    }
}
