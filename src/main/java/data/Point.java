package data;

/*
 * data.Point.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

/**
 * The data.Point class for implementing the Octree.
 *
 * @author Siddesh Pillai
 */

public class Point {

    Point[] childPoints;
    public double x, y, z;

    public Point(double _x, double _y, double _z) {

        this.x = _x;
        this.y = _y;
        this.z = _z;

        this.childPoints = new Point[8];

    }

}