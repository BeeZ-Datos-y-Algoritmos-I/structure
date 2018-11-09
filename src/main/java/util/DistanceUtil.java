package util;

public class DistanceUtil {

    public static double poweredDistance(double[] pointA, double[] pointB) {

        double distance = 0;

        for (int i = 0; i < pointA.length; ++i)
            distance += Math.pow((pointA[i] - pointB[i]), 2);

        return distance;
    }

    public static double euclideanDistance(double[] pointA, double[] pointB) {
        return Math.sqrt(poweredDistance(pointA, pointB));
    }

    public static double hammingDistance(double[] pointA, double[] pointB) {

        double distance = 0;

        for (int i = 0; i < pointA.length; ++i)
            distance += Math.abs((pointA[i] - pointB[i]));

        return distance;
    }

}
