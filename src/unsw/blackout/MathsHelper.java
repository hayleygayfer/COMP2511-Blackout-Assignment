package unsw.blackout;

import java.lang.Math;

public class MathsHelper {
    public static final int RING_RADIUS = 3000;

    public static boolean satelliteIsVisibleFromDevice(double satelliteAngle, double satelliteHeight,
            double deviceAngle) {
        if (satelliteHeight <= RING_RADIUS) {
            throw new RuntimeException("You can't have a satellite 'inside' the ring or just touching the ring, make sure to include the ring radius in your heights");
        }
        
        // convert to euclidean
        double satX = Math.cos(Math.toRadians(satelliteAngle)) * satelliteHeight, satY = Math.sin(Math.toRadians(satelliteAngle)) * satelliteHeight;
        double devX = Math.cos(Math.toRadians(deviceAngle)) * RING_RADIUS, devY = Math.sin(Math.toRadians(deviceAngle)) * RING_RADIUS;

        // find length of line between euclidean points
        double length = Math.sqrt((satX - devX) * (satX - devX) + (satY - devY) * (satY - devY));
        return length < satelliteHeight;
    }
}
