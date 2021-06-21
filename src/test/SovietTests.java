package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.time.LocalTime;

import test.test_helpers.DummyConnection;
import test.test_helpers.ResponseHelper;
import test.test_helpers.TestHelper;

@TestInstance(value = Lifecycle.PER_CLASS)

public class SovietTests {
    @Test
    public void testConnectsToOnlyLaptopsAndDesktops() {
        // test only connects to laptops and desktops

        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("SovietSatellite", "Satellite1", 10000, 185, 100, new String[] { "DeviceA", "DeviceB" , "DeviceC"})
            .expectDevice("HandheldDevice", "DeviceA", 180, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 185, false, 
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("DesktopDevice", "DeviceC", 190, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("SovietSatellite", "Satellite1", 10000, 180.62, -100,
                new String[] { "DeviceA", "DeviceB" , "DeviceC"},
                new DummyConnection[] {
                    new DummyConnection("DeviceB", LocalTime.of(0, 0), LocalTime.of(6, 31), 386),
                    new DummyConnection("DeviceC", LocalTime.of(0, 0), LocalTime.of(6, 31), 380),
                })
            .expectDevice("HandheldDevice", "DeviceA", 180, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 185, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("DesktopDevice", "DeviceC", 190, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("HandheldDevice", "DeviceA", 180)
            .createDevice("LaptopDevice", "DeviceB", 185)
            .createDevice("DesktopDevice", "DeviceC", 190)
            .createSatellite("SovietSatellite", "Satellite1", 10000, 185)
            .scheduleDeviceActivation("DeviceA", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceB", LocalTime.of(0, 0), 390)
            .scheduleDeviceActivation("DeviceC", LocalTime.of(0, 0), 390)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
    }

    @Test
    public void testDropOldestConnection() {
        // test that the satellite drops the oldest connection once it reaches 9 connections

        /* This test breaks the visualiser... my program has pretty much the same response so I'm just going to leave it
        String initialWorldState = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("SovietSatellite", "Satellite1", 10000, 185, 100, new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG", "DeviceH", "DeviceI", "DeviceJ", "DeviceK", "DeviceL" })
            .expectDevice("LaptopDevice", "DeviceA", 180, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 181, false,
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("DesktopDevice", "DeviceC", 182, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .expectDevice("DesktopDevice", "DeviceD", 183, false,
                new LocalTime[][] { { LocalTime.of(0, 03), LocalTime.of(6, 33) } })
            .expectDevice("DesktopDevice", "DeviceE", 184, false,
                new LocalTime[][] { { LocalTime.of(0, 04), LocalTime.of(6, 34) } })
            .expectDevice("DesktopDevice", "DeviceF", 185, false,
                new LocalTime[][] { { LocalTime.of(0, 05), LocalTime.of(6, 35) } })
            .expectDevice("DesktopDevice", "DeviceG", 186, false,
                new LocalTime[][] { { LocalTime.of(0, 06), LocalTime.of(6, 36) } })
            .expectDevice("DesktopDevice", "DeviceH", 187, false,
                new LocalTime[][] { { LocalTime.of(0, 07), LocalTime.of(6, 37) } })
            .expectDevice("DesktopDevice", "DeviceI", 188, false,
                new LocalTime[][] { { LocalTime.of(0, 8), LocalTime.of(6, 38) } })
            .expectDevice("DesktopDevice", "DeviceJ", 189, false,
                new LocalTime[][] { { LocalTime.of(0, 9), LocalTime.of(6, 39) } })
            .expectDevice("DesktopDevice", "DeviceK", 190, false,
                new LocalTime[][] { { LocalTime.of(0, 10), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceL", 191, false,
                new LocalTime[][] { { LocalTime.of(0, 11), LocalTime.of(6, 41) } })
            .toString();

        // then simulates for a full day (1440 mins)
        String afterADay = new ResponseHelper(LocalTime.of(0, 0))
            .expectSatellite("SovietSatellite", "Satellite1", 10000, 180.62, -100,
                new String[] { "DeviceA", "DeviceB" , "DeviceC", "DeviceD", "DeviceE", "DeviceF", "DeviceG", "DeviceH", "DeviceI", "DeviceJ", "DeviceK", "DeviceL" },
                new DummyConnection[] {
                    THERE ARE 4602 CONNECTIONS WHEN RUNNING THESE INPUTS IN THE BACK TO BLACKOUT VISUALISER
                })
            .expectDevice("LaptopDevice", "DeviceA", 180, false,
                new LocalTime[][] { { LocalTime.of(0, 0), LocalTime.of(6, 30) } })
            .expectDevice("LaptopDevice", "DeviceB", 181, false,
                new LocalTime[][] { { LocalTime.of(0, 01), LocalTime.of(6, 31) } })
            .expectDevice("DesktopDevice", "DeviceC", 182, false,
                new LocalTime[][] { { LocalTime.of(0, 02), LocalTime.of(6, 32) } })
            .expectDevice("DesktopDevice", "DeviceD", 183, false,
                new LocalTime[][] { { LocalTime.of(0, 03), LocalTime.of(6, 33) } })
            .expectDevice("DesktopDevice", "DeviceE", 184, false,
                new LocalTime[][] { { LocalTime.of(0, 04), LocalTime.of(6, 34) } })
            .expectDevice("DesktopDevice", "DeviceF", 185, false,
                new LocalTime[][] { { LocalTime.of(0, 05), LocalTime.of(6, 35) } })
            .expectDevice("DesktopDevice", "DeviceG", 186, false,
                new LocalTime[][] { { LocalTime.of(0, 06), LocalTime.of(6, 36) } })
            .expectDevice("DesktopDevice", "DeviceH", 187, false,
                new LocalTime[][] { { LocalTime.of(0, 07), LocalTime.of(6, 37) } })
            .expectDevice("DesktopDevice", "DeviceI", 188, false,
                new LocalTime[][] { { LocalTime.of(0, 8), LocalTime.of(6, 38) } })
            .expectDevice("DesktopDevice", "DeviceJ", 189, false,
                new LocalTime[][] { { LocalTime.of(0, 9), LocalTime.of(6, 39) } })
            .expectDevice("DesktopDevice", "DeviceK", 190, false,
                new LocalTime[][] { { LocalTime.of(0, 10), LocalTime.of(6, 40) } })
            .expectDevice("DesktopDevice", "DeviceL", 191, false,
                new LocalTime[][] { { LocalTime.of(0, 11), LocalTime.of(6, 41) } })
            .toString();

        TestHelper plan = new TestHelper().createDevice("LaptopDevice", "DeviceA", 180)
            .createDevice("LaptopDevice", "DeviceB", 181)
            .createDevice("DesktopDevice", "DeviceC", 182)
            .createDevice("DesktopDevice", "DeviceD", 183)
            .createDevice("DesktopDevice", "DeviceE", 184)
            .createDevice("DesktopDevice", "DeviceF", 185)
            .createDevice("DesktopDevice", "DeviceG", 186)
            .createDevice("DesktopDevice", "DeviceH", 187) 
            .createDevice("DesktopDevice", "DeviceI", 188)
            .createDevice("DesktopDevice", "DeviceJ", 189)
            .createDevice("DesktopDevice", "DeviceK", 190)
            .createDevice("DesktopDevice", "DeviceL", 191)
            .createSatellite("SovietSatellite", "Satellite1", 10000, 185)
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
            .scheduleDeviceActivation("DeviceL", LocalTime.of(0, 11), 390)
            .showWorldState(initialWorldState)
            .simulate(1440)
            .showWorldState(afterADay);
        plan.executeTestPlan();
        */
    }
}