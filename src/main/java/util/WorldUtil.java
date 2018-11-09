package util;

public class WorldUtil {

    public static final double SPLIT2 = 100 / Math.sqrt(2);
    public static final double SPLIT3 = 100 / Math.sqrt(3);

    public static final double LAYER3 = Math.cbrt(100);

    public static int getLayerSize(double max, double min) {
        return (int) Math.ceil((max - min) / LAYER3);
    }

    public static int getFloorPosition(double max, double min) {
        return (int) Math.floor((max - min) / LAYER3);
    }

}
