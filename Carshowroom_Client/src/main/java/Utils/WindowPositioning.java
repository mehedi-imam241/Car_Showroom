package Utils;

import java.util.Random;

public class WindowPositioning {
    private static Double x = new Random().doubles(500.0,600.0).findAny().getAsDouble();
    private static Double y = new Random().doubles(200.0,350.0).findAny().getAsDouble();;

    public static Double getX() {
        return x;
    }

    public static void setX(Double x) {
        WindowPositioning.x = x;
    }

    public static Double getY() {
        return y;
    }

    public static void setY(Double y) {
        WindowPositioning.y = y;
    }
}
