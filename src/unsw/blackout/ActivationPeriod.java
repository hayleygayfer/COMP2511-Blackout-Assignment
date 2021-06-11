package unsw.blackout;

import java.time.LocalTime;

public class ActivationPeriod {
    //change these types later
    private LocalTime startTime;
    private int durationInMinutes;

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

    public String getString() {
        return "startTime: " + startTime + "\nendTime: " + getEndTime() + "\n";
    }

    public boolean isDuring(LocalTime currentTime) {
        if (currentTime.isAfter(startTime) && currentTime.isBefore(getEndTime())) return true;
        else return false;
    }
}
