package data;

import javafx.geometry.Point3D;
import util.CoordUtil;

public class Bee {

    public static final double RADIUS = 100.0;

    private double[] xyz;
    private double[] cartesian;

    private boolean collide = false;
    private Point3D cartesianPoint;

    public Bee(double x, double y, double z) {

        this.xyz = new double[]{x, y, z};
        this.cartesianPoint = CoordUtil.getPointXYZfromLatLongDegress(toPoint3D());

        this.cartesian = new double[]{cartesianPoint.getX(),
                cartesianPoint.getY(),
                cartesianPoint.getZ()};

    }

    public Bee(double x, double y, double z, Point3D cartesian) {

        this.xyz = new double[]{x, y, z};
        this.cartesian = new double[]{cartesian.getX(),
                cartesian.getY(),
                cartesian.getZ()};

        this.cartesianPoint = cartesian;

    }

    public void setCollide(boolean collide) {
        this.collide = collide;
        return;
    }

    public boolean hasCollide() {
        return collide;
    }

    public double getX() {
        return xyz[0];
    }

    public double getY() {
        return xyz[1];
    }

    public double getZ() {
        return xyz[2];
    }

    public double getCartesianX() {
        return cartesianPoint.getX();
    }

    public double getCartesianY() {
        return cartesianPoint.getY();
    }

    public double getCartesianZ() {
        return cartesianPoint.getZ();
    }

    public double[] toArr3D() {
        return xyz;
    }

    ;

    public double[] toCartesianArr3D() {
        return cartesian;
    }

    ;

    public Point3D toPoint3D() {
        return new Point3D(xyz[0],
                xyz[1],
                xyz[2]);
    }

    public Point3D toCartesianPoint3D() {
        return cartesianPoint;
    }

}
