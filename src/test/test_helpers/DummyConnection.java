package test.test_helpers;

import java.time.LocalTime;

public class DummyConnection {
    private String deviceId;
    private LocalTime startTime;
    private LocalTime endTime;

    public DummyConnection(String deviceId, LocalTime startTime, LocalTime endTime) {
        this.deviceId = deviceId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DummyConnection(String deviceId, LocalTime startTime) {
        this.deviceId = deviceId;
        this.startTime = startTime;
        this.endTime = null;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
