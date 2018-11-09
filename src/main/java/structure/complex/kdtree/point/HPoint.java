package structure.complex.kdtree.point;

import util.DistanceUtil;

import java.io.Serializable;

public class HPoint implements Serializable {

    public double[] xyzCoord;

    public HPoint(int n) {
        xyzCoord = new double[n];
    }

    public HPoint(double[] x) {

        xyzCoord = new double[x.length];
        for (int i = 0; i < x.length; ++i)
            xyzCoord[i] = x[i];

    }

    public Object clone() {
        return new HPoint(xyzCoord);
    }

    public boolean equals(HPoint p) {

        for (int i = 0; i < xyzCoord.length; ++i)
            if (xyzCoord[i] != p.xyzCoord[i])
                return false;

        return true;

    }

    public static double sqrdist(HPoint x, HPoint y) {
        return DistanceUtil.euclideanDistance(x.xyzCoord, y.xyzCoord);
    }

    public String toString() {

        String result = "";

        for (int i = 0; i < xyzCoord.length; ++i)
            result += xyzCoord[i] + " ";

        return result;

    }

}
