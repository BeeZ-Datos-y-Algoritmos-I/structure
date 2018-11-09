package structure.complex.matrix3d.compose;

import data.Bee;
import javafx.geometry.Point3D;
import util.WorldUtil;

import java.util.LinkedList;

public class Matrix3DSpace {

    private Point3D max, min, length;
    private LinkedList<Bee> spatial[][][];

    public Matrix3DSpace(Point3D max, Point3D min) {

        this.max = max;
        this.min = min;

        int xsize = WorldUtil.getLayerSize(max.getX(), min.getX()),
                ysize = WorldUtil.getLayerSize(max.getY(), min.getY()),
                zsize = WorldUtil.getLayerSize(max.getZ(), min.getZ());

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
