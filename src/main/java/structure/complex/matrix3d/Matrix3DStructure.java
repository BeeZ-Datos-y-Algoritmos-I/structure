package structure.complex.matrix3d;

import data.Bee;
import reader.best.AllLinesNIOEdgesPrimitiveReader;
import structure.common.IStructure;
import structure.complex.matrix3d.compose.Matrix3DSpace;
import util.WorldUtil;

import java.util.Date;
import java.util.LinkedList;

public class Matrix3DStructure implements IStructure {

    private Matrix3DSpace matrix;
    private AllLinesNIOEdgesPrimitiveReader reader;

    @Override
    public long craft() {

        long millis = new Date().getTime();

        matrix = new Matrix3DSpace(reader.getMaxBound(), reader.getMinBound());

        return new Date().getTime() - millis;
    }

    @Override
    public long read() {

        long millis = new Date().getTime();

        reader = new AllLinesNIOEdgesPrimitiveReader();
        reader.load();

        return new Date().getTime() - millis;
    }

    @Override
    public long insert() {

        long millis = new Date().getTime();

        for (Bee bee : reader.getBees()) {

            int posx = WorldUtil.getFloorPosition(bee.getX(), reader.getMinBound().getX()),
                    posy = WorldUtil.getFloorPosition(bee.getY(), reader.getMinBound().getY()),
                    posz = WorldUtil.getFloorPosition(bee.getZ(), reader.getMinBound().getZ());

            if (matrix.get(posx, posy, posz) == null)
                matrix.insert(posx, posy, posz);

            matrix.get(posx, posy, posz).add(bee);

        }

        System.out.println("[MATRIX3D] Tamaño de la Matriz 3D (" + matrix.getSizeX() * matrix.getSizeY() * matrix.getSizeZ() + ")");
        return new Date().getTime() - millis;
    }

    @Override
    public long detect() {

        LinkedList<Bee> collide = new LinkedList<Bee>();
        long millis = new Date().getTime();

        for (int x = 0; x < matrix.getSizeX(); x++)
            for (int y = 0; y < matrix.getSizeY(); y++)
                for (int z = 0; z < matrix.getSizeZ(); z++) {

                    int count = matrix.count(x, y, z);

                    if (count > -1)
                        for (int k = 0; k < count; k++)
                            collide.add(matrix.get(x, y, z).get(k));
                    else if (matrix.get(x, y, z) != null) {

                        boolean collision = false;

                        double x1 = matrix.get(x, y, z).get(0).getX();
                        double y1 = matrix.get(x, y, z).get(0).getY();
                        double z1 = matrix.get(x, y, z).get(0).getZ();

                        for (int deltaX = (x > 0 ? -1 : 0); deltaX <= (x < matrix.getSizeX() - 1 ? 1 : 0) && !collision; deltaX++) {
                            for (int deltaY = (y > 0 ? -1 : 0); deltaY <= (y < matrix.count(x) - 1 ? 1 : 0) && !collision; deltaY++) {
                                for (int deltaZ = (z > 0 ? -1 : 0); deltaZ <= (z < matrix.count(x, y) - 1 ? 1 : 0) && !collision; deltaZ++) {
                                    if (deltaX != 0 || deltaY != 0 || deltaZ != 0) {
                                        if (matrix.get(x + deltaX, y + deltaY, z + deltaZ) != null) {
                                            for (int w = 0; w < matrix.count(x + deltaX, y + deltaY, z + deltaZ) && !collision; w++) {

                                                LinkedList<Bee> node = matrix.get(x + deltaX, y + deltaY, z + deltaZ);

                                                double x2 = node.get(w).getX();
                                                double y2 = node.get(w).getY();
                                                double z2 = node.get(w).getZ();

                                                if (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) + Math.pow(z1 - z2, 2) <= 10000) {
                                                    collision = true;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (collision) {
                            collide.add(matrix.get(x, y, z).get(0));
                        }
                    }
                }

        System.out.println("[MATRIX3D] Colisiones posibles: " + collide.size());
        return new Date().getTime() - millis;
    }

    @Override
    public long save() {
        return 0;
    }

}
