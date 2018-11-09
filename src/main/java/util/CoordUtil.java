package util;

import javafx.geometry.Point3D;

public class CoordUtil {

    public static final double DEGREES_TO_RADIANS = Math.PI / 180.0;

    public static double[] getXYZfromLatLonRadians(double latitude, double longitude, double height) {
        double a = 6378137.0;
        double b = 6356752.3142;
        double cosLat = Math.cos(latitude);
        double sinLat = Math.sin(latitude);


        double rSubN = (a * a) / Math.sqrt(((a * a) * (cosLat * cosLat) + ((b * b) * (sinLat * sinLat))));

        double X = (rSubN + height) * cosLat * Math.cos(longitude);
        double Y = (rSubN + height) * cosLat * Math.sin(longitude);
        double Z = ((((b * b) / (a * a)) * rSubN) + height) * sinLat;

        return new double[]{X, Y, Z};

    }

    public static double[] getXYZfromLatLonDegrees(double latitude, double longitude, double height) {
        double degrees[] = CoordUtil.getXYZfromLatLonRadians(latitude * CoordUtil.DEGREES_TO_RADIANS,
                longitude * CoordUtil.DEGREES_TO_RADIANS,
                height);

        return degrees;
    }

    public static Point3D getPointXYZfromLatLongDegress(Point3D point) {

        double[] xyz = getXYZfromLatLonDegrees(point.getX(), point.getY(), point.getZ());
        return new Point3D(xyz[0], xyz[1], xyz[2]);

    }

}