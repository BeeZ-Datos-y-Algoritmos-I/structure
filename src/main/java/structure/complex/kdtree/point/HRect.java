package structure.complex.kdtree.point;

import java.io.Serializable;

public class HRect implements Serializable {

    public HPoint min;
    public HPoint max;

    public HRect(int ndims) {
        min = new HPoint(ndims);
        max = new HPoint(ndims);
    }

    public HRect(HPoint vmin, HPoint vmax) {
        min = (HPoint) vmin.clone();
        max = (HPoint) vmax.clone();
    }

    public Object clone() {
        return new HRect(min, max);
    }

    public HPoint closest(HPoint t) {

        HPoint p = new HPoint(t.xyzCoord.length);

        for (int i = 0; i < t.xyzCoord.length; ++i)
            if (t.xyzCoord[i] <= min.xyzCoord[i])
                p.xyzCoord[i] = min.xyzCoord[i];
            else if (t.xyzCoord[i] >= max.xyzCoord[i])
                p.xyzCoord[i] = max.xyzCoord[i];
            else
                p.xyzCoord[i] = t.xyzCoord[i];

        return p;
    }

    public static HRect infiniteHRect(int d) {

        HPoint vmin = new HPoint(d);
        HPoint vmax = new HPoint(d);

        for (int i = 0; i < d; ++i) {
            vmin.xyzCoord[i] = Double.NEGATIVE_INFINITY;
            vmax.xyzCoord[i] = Double.POSITIVE_INFINITY;
        }

        return new HRect(vmin, vmax);
    }

    public HRect intersection(HRect r) {

        HPoint newmin = new HPoint(min.xyzCoord.length);
        HPoint newmax = new HPoint(min.xyzCoord.length);

        for (int i = 0; i < min.xyzCoord.length; ++i) {

            newmin.xyzCoord[i] = Math.max(min.xyzCoord[i], r.min.xyzCoord[i]);
            newmax.xyzCoord[i] = Math.min(max.xyzCoord[i], r.max.xyzCoord[i]);

            if (newmin.xyzCoord[i] >= newmax.xyzCoord[i])
                return null;

        }

        return new HRect(newmin, newmax);

    }

    public double area() {

        double area = 1;

        for (int i = 0; i < min.xyzCoord.length; ++i)
            area *= (max.xyzCoord[i] - min.xyzCoord[i]);

        return area;

    }

    public String toString() {
        return min + "\n" + max + "\n";
    }

}

