package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalTime;

import test.test_helpers.DummyConnection;
import test.test_helpers.ResponseHelper;
import test.test_helpers.TestHelper;

@TestInstance(value = Lifecycle.PER_CLASS)

public class BlueOriginTests {
    @Test
    public void testConnectsToAllDevices() {
        // test connects to all devices

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66, new String[] { "DeviceA", "DeviceB" , "DeviceC"})
            .expectDevice("HandheldDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("DesktopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(4, 50) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.4, 141.66,
                new String[] { "DeviceA", "DeviceB" , "DeviceC"},
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 31), 389),
                    new DummyConnection("DeviceB", LocalTime.of(0, 0), LocalTime.of(6, 31), 388), //
                    new DummyConnection("DeviceC", LocalTime.of(0, 0), LocalTime.of(4, 51), 285), //
                })
            .expectDevice("HandheldDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 340, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("DesktopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(4, 50) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("HandheldDevice", "DeviceA", 30)
            .createDevice("LaptopDevice", "DeviceB", 340)
            .createDevice("DesktopDevice", "DeviceC", 330)
            .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 0), 290)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }

    @Test
    public void testSeperateActivationPeriods() {
        // test that the satellite can handle connecting to the same device twice in a row
        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66, new String[] { "DeviceA" })
            .expectDevice("HandheldDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) }, { LocalTime.of(6, 40), LocalTime.of(10, 00) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.4, 141.66,
                new String[] { "DeviceA" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 31), 389),
                    new DummyConnection("DeviceA", LocalTime.of(6, 40), LocalTime.of(10, 01), 199)
                })
            .expectDevice("HandheldDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) }, { LocalTime.of(6, 40), LocalTime.of(10, 00) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("HandheldDevice", "DeviceA", 30)
            .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(6, 40), 200)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }

    @Test
    public void testMaxLaptops() {
        // test that the satellite does not connect to more than 5 laptops

        // test connects to all devices

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66, new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF" })
            .expectDevice("LaptopDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("LaptopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .expectDevice("LaptopDevice", "DeviceD", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 03), LocalTime.of(6, 33) } })
            .expectDevice("LaptopDevice", "DeviceE", 310, false,
                new LocalTime[][] { { LocalTime.of(0, 04), LocalTime.of(6, 34) } })
            .expectDevice("LaptopDevice", "DeviceF", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 05), LocalTime.of(6, 35) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.4, 141.66,
                new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 31), 388),
                    new DummyConnection("DeviceB", LocalTime.of(0, 01), LocalTime.of(6, 32), 388), //
                    new DummyConnection("DeviceC", LocalTime.of(0, 02), LocalTime.of(6, 33), 388), //
                    new DummyConnection("DeviceD", LocalTime.of(0, 03), LocalTime.of(6, 34), 388), //
                    new DummyConnection("DeviceE", LocalTime.of(0, 04), LocalTime.of(6, 35), 388), //
                    new DummyConnection("DeviceF", LocalTime.of(6, 31), LocalTime.of(6, 36), 2), //
                })
            .expectDevice("LaptopDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("LaptopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .expectDevice("LaptopDevice", "DeviceD", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 03), LocalTime.of(6, 33) } })
            .expectDevice("LaptopDevice", "DeviceE", 310, false,
                new LocalTime[][] { { LocalTime.of(0, 04), LocalTime.of(6, 34) } })
            .expectDevice("LaptopDevice", "DeviceF", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 05), LocalTime.of(6, 35) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("LaptopDevice", "DeviceA", 30)
            .createDevice("LaptopDevice", "DeviceB", 340)
            .createDevice("LaptopDevice", "DeviceC", 330)
            .createDevice("LaptopDevice", "DeviceD", 320)
            .createDevice("LaptopDevice", "DeviceE", 310)
            .createDevice("LaptopDevice", "DeviceF", 350)
            .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 01), 390)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 02), 390)
            .scheduleDeviceActivation("DeviceD", LocalTime.of(0, 03), 390)
            .scheduleDeviceActivation("DeviceE", LocalTime.of(0, 04), 390)
            .scheduleDeviceActivation("DeviceF", LocalTime.of(0, 05), 390)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }

    @Test
    public void testMaxDesktops() {
        // test that the satellite does not connect to more than 2 desktops

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66, new String[] { "DeviceA", "DeviceB" , "DeviceC" })
            .expectDevice("DesktopDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("DesktopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("DesktopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.4, 141.66,
                new String[] { "DeviceA", "DeviceB" , "DeviceC" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 31), 385),
                    new DummyConnection("DeviceB", LocalTime.of(0, 01), LocalTime.of(6, 32), 385), //
                    new DummyConnection("DeviceC", LocalTime.of(6, 31), LocalTime.of(6, 33), 0), //
                })
            .expectDevice("DesktopDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("DesktopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("DesktopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("DesktopDevice", "DeviceA", 30)
            .createDevice("DesktopDevice", "DeviceB", 340)
            .createDevice("DesktopDevice", "DeviceC", 330)
            .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 01), 390)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 02), 390)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }

    @Test
    public void testTotalMax() {
        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 340, 141.66, new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG", "DeviceH", "DeviceI", "DeviceJ", "DeviceK" })
            .expectDevice("LaptopDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("LaptopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .expectDevice("LaptopDevice", "DeviceD", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 03), LocalTime.of(6, 33) } })
            .expectDevice("LaptopDevice", "DeviceE", 310, false,
                new LocalTime[][] { { LocalTime.of(0, 04), LocalTime.of(6, 34) } })
            .expectDevice("DesktopDevice", "DeviceF", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 05), LocalTime.of(6, 35) } })
            .expectDevice("DesktopDevice", "DeviceG", 345, false,
                new LocalTime[][] { { LocalTime.of(0, 06), LocalTime.of(6, 36) } })
            .expectDevice("HandheldDevice", "DeviceH", 346, false,
                new LocalTime[][] { { LocalTime.of(0, 07), LocalTime.of(6, 37) } })
            .expectDevice("HandheldDevice", "DeviceI", 347, false,
                new LocalTime[][] { { LocalTime.of(0, 8), LocalTime.of(6, 38) } })
            .expectDevice("HandheldDevice", "DeviceJ", 348, false,
                new LocalTime[][] { { LocalTime.of(0, 9), LocalTime.of(6, 39) } })
            .expectDevice("HandheldDevice", "DeviceK", 349, false,
                new LocalTime[][] { { LocalTime.of(0, 10), LocalTime.of(6, 40) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("BlueOriginSatellite", "Satellite1", 10000, 0.4, 141.66,
                new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG", "DeviceH", "DeviceI", "DeviceJ", "DeviceK" },
                new DummyConnection[] {
                    new DummyConnection("DeviceA", LocalTime.of(0, 0), LocalTime.of(6, 31), 388),
                    new DummyConnection("DeviceB", LocalTime.of(0, 01), LocalTime.of(6, 32), 388), //
                    new DummyConnection("DeviceC", LocalTime.of(0, 02), LocalTime.of(6, 33), 388), //
                    new DummyConnection("DeviceD", LocalTime.of(0, 03), LocalTime.of(6, 34), 388), //
                    new DummyConnection("DeviceE", LocalTime.of(0, 04), LocalTime.of(6, 35), 388), //
                    new DummyConnection("DeviceF", LocalTime.of(0, 05), LocalTime.of(6, 36), 385),
                    new DummyConnection("DeviceG", LocalTime.of(0, 06), LocalTime.of(6, 37), 385), //
                    new DummyConnection("DeviceH", LocalTime.of(0, 07), LocalTime.of(6, 38), 389), //
                    new DummyConnection("DeviceI", LocalTime.of(0, 8), LocalTime.of(6, 39), 389), //
                    new DummyConnection("DeviceJ", LocalTime.of(0, 9), LocalTime.of(6, 40), 389), //
                    new DummyConnection("DeviceK", LocalTime.of(6, 31), LocalTime.of(6, 41), 8),
                })
            .expectDevice("LaptopDevice", "DeviceA", 30, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 340, false, 
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("LaptopDevice", "DeviceC", 330, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .expectDevice("LaptopDevice", "DeviceD", 320, false,
                new LocalTime[][] { { LocalTime.of(0, 03), LocalTime.of(6, 33) } })
            .expectDevice("LaptopDevice", "DeviceE", 310, false,
                new LocalTime[][] { { LocalTime.of(0, 04), LocalTime.of(6, 34) } })
            .expectDevice("DesktopDevice", "DeviceF", 350, false,
                new LocalTime[][] { { LocalTime.of(0, 05), LocalTime.of(6, 35) } })
            .expectDevice("DesktopDevice", "DeviceG", 345, false,
                new LocalTime[][] { { LocalTime.of(0, 06), LocalTime.of(6, 36) } })
            .expectDevice("HandheldDevice", "DeviceH", 346, false,
                new LocalTime[][] { { LocalTime.of(0, 07), LocalTime.of(6, 37) } })
            .expectDevice("HandheldDevice", "DeviceI", 347, false,
                new LocalTime[][] { { LocalTime.of(0, 8), LocalTime.of(6, 38) } })
            .expectDevice("HandheldDevice", "DeviceJ", 348, false,
                new LocalTime[][] { { LocalTime.of(0, 9), LocalTime.of(6, 39) } })
            .expectDevice("HandheldDevice", "DeviceK", 349, false,
                new LocalTime[][] { { LocalTime.of(0, 10), LocalTime.of(6, 40) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("LaptopDevice", "DeviceA", 30)
            .createDevice("LaptopDevice", "DeviceB", 340)
            .createDevice("LaptopDevice", "DeviceC", 330)
            .createDevice("LaptopDevice", "DeviceD", 320)
            .createDevice("LaptopDevice", "DeviceE", 310)
            .createDevice("DesktopDevice", "DeviceF", 350)
            .createDevice("DesktopDevice", "DeviceG", 345)
            .createDevice("HandheldDevice", "DeviceH", 346)
            .createDevice("HandheldDevice", "DeviceI", 347)
            .createDevice("HandheldDevice", "DeviceJ", 348)
            .createDevice("HandheldDevice", "DeviceK", 349)
            .createSatellite("BlueOriginSatellite", "Satellite1", 10000, 340)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 01), 390)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 02), 390)
            .scheduleDeviceActivation("DeviceD", LocalTime.of(0, 03), 390)
            .scheduleDeviceActivation("DeviceE", LocalTime.of(0, 04), 390)
            .scheduleDeviceActivation("DeviceF", LocalTime.of(0, 05), 390)
            .scheduleDeviceActivation("DeviceG", LocalTime.of(0, 06), 390)
            .scheduleDeviceActivation("DeviceH", LocalTime.of(0, 07), 390)
            .scheduleDeviceActivation("DeviceI", LocalTime.of(0, 8), 390)
            .scheduleDeviceActivation("DeviceJ", LocalTime.of(0, 9), 390)
            .scheduleDeviceActivation("DeviceK", LocalTime.of(0, 10), 390)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }
}
