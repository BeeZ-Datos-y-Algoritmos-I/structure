package structure.complex.matrix3d.compose;

import data.Bee;
import javafx.geometry.Point3D;

import java.util.LinkedList;

public class Matrix3DSpace {

    private Point3D max, min, length;
    private LinkedList<Bee> spatial[][][];

    public Matrix3DSpace(Point3D max, Point3D min) {

        this.max = max;
        this.min = min;

        int xsize = (int) ((max.getX() - min.getX()) * 100000 / 70.72),
                ysize = (int) ((max.getY() - min.getY()) * 100000 / 70.72),
                zsize = (int) ((max.getZ() - min.getZ()) / 70.72);

        spatial = new LinkedList[xsize][ysize][zsize];
        length = new Point3D(xsize, ysize, zsize);

    }

    public void insert(int x, int y, int z) {
        spatial[x][y][z] = new LinkedList<Bee>();
    }

    public LinkedList<Bee> get(int x, int y, int z) {
        return spatial[x][y][z];
    }

    public int count(int x) {
        return spatial[x].length;
    }

    public int count(int x, int y) {
        return spatial[x][y].length;
    }

    public int count(int x, int y, int z) {

        if (spatial[x][y][z] == null)
            return -1;

        return spatial[x][y][z].size();
    }

    public LinkedList<Bee>[][][] getSpatialWorld() {
        return spatial;
    }

    public int getSizeX() {
        return (int) length.getX();
    }

    public int getSizeY() {
        return (int) length.getY();
    }

    public int getSizeZ() {
        return (int) length.getZ();
    }

}
