package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.time.LocalTime;

import test.test_helpers.DummyConnection;
import test.test_helpers.ResponseHelper;
import test.test_helpers.TestHelper;

@TestInstance(value = Lifecycle.PER_CLASS)
public class Task2ExtraTests {
    @Test
    public void testSmallAngle() {
        // Task 2
        // Smallest 'angle' takes priority over larger ones...

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("NasaSatellite", "Satellite1", 10000, 30, 85, new String[] { "DeviceA", "DeviceB", "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG" })
            .expectDevice("HandheldDevice", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("LaptopDevice", "DeviceB", 31, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceC", 32, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceD", 33, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("HandheldDevice", "DeviceE", 34, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("LaptopDevice", "DeviceF", 35, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceG", 36, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .toString();
        // NASA can only connect to 6 devices so based on 'smallest' angle priority... which one goes first?

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("NasaSatellite", "Satellite1", 10000, 42.24, 85,
                new String[] { "DeviceA", "DeviceB", "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 41), 390),
                    new DummyConnection("DeviceB", LocalTime.of(0, 0), LocalTime.of(6, 41), 390),
                    new DummyConnection("DeviceC", LocalTime.of(0, 0), LocalTime.of(6, 41), 390),
                    new DummyConnection("DeviceD", LocalTime.of(0, 0), LocalTime.of(6, 41), 390),
                    new DummyConnection("DeviceE", LocalTime.of(0, 0), LocalTime.of(6, 41), 390),
                    new DummyConnection("DeviceF", LocalTime.of(0, 0), LocalTime.of(6, 41), 390), //
                })
            .expectDevice("HandheldDevice", "DeviceA", 30, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("LaptopDevice", "DeviceB", 31, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceC", 32, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceD", 33, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("HandheldDevice", "DeviceE", 34, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("LaptopDevice", "DeviceF", 35, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceG", 36, false, new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 40) } })
            .toString();

        TestHelper plan = new TestHelper()
            .createDevice("HandheldDevice", "DeviceA", 30)
            .createDevice("LaptopDevice", "DeviceB", 31)
            .createDevice("DesktopDevice", "DeviceC", 32)
            .createDevice("DesktopDevice", "DeviceD", 33)
            .createDevice("HandheldDevice", "DeviceE", 34)
            .createDevice("LaptopDevice", "DeviceF", 35)
            .createDevice("DesktopDevice", "DeviceG", 36)
            .createSatellite("NasaSatellite", "Satellite1", 10000, 30)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 400)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 0), 400)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 0), 400)
            .scheduleDeviceActivation("DeviceD", LocalTime.of(0, 0), 400)
            .scheduleDeviceActivation("DeviceE", LocalTime.of(0, 0), 400)
            .scheduleDeviceActivation("DeviceF", LocalTime.of(0, 0), 400)
            .scheduleDeviceActivation("DeviceG", LocalTime.of(0, 0), 400)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }
}
