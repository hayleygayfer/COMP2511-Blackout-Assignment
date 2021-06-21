package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.time.LocalTime;

import test.test_helpers.DummyConnection;
import test.test_helpers.ResponseHelper;
import test.test_helpers.TestHelper;

@TestInstance(value = Lifecycle.PER_CLASS)

public class NasaTests {
    @Test
    public void testMaxDevicesInRange() {
        // test that the satellite drops oldest connection out of range if a new device attempting to connect is 
        // in the [30, 40] range

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("NasaSatellite", "Satellite1", 10000, 50, 85, new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG" })
            .expectDevice("LaptopDevice", "DeviceA", 41, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 42, false, 
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceC", 43, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceD", 44, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceE", 45, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceF", 46, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceG", 35, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("NasaSatellite", "Satellite1", 10000, 62.24, 85,
                new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(0, 0), 0),
                    new DummyConnection("DeviceB", LocalTime.of(0, 0), LocalTime.of(6, 31), 380), //
                    new DummyConnection("DeviceC", LocalTime.of(0, 0), LocalTime.of(6, 31), 380), //
                    new DummyConnection("DeviceD", LocalTime.of(0, 0), LocalTime.of(6, 31), 380), //
                    new DummyConnection("DeviceE", LocalTime.of(0, 0), LocalTime.of(6, 31), 380), //
                    new DummyConnection("DeviceF", LocalTime.of(0, 0), LocalTime.of(6, 31), 380), //
                    new DummyConnection("DeviceG", LocalTime.of(0, 0), LocalTime.of(6, 31), 380), //
                })
            .expectDevice("LaptopDevice", "DeviceA", 41, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 42, false, 
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceC", 43, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceD", 44, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceE", 45, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceF", 46, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceG", 35, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("LaptopDevice", "DeviceA", 41)
            .createDevice("LaptopDevice", "DeviceB", 42)
            .createDevice("LaptopDevice", "DeviceC", 43)
            .createDevice("LaptopDevice", "DeviceD", 44)
            .createDevice("LaptopDevice", "DeviceE", 45)
            .createDevice("LaptopDevice", "DeviceF", 46)
            .createDevice("LaptopDevice", "DeviceG", 35)
            .createSatellite("NasaSatellite", "Satellite1", 10000, 50)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceD", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceE", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceF", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceG", LocalTime.of(0, 0), 390)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }
}
