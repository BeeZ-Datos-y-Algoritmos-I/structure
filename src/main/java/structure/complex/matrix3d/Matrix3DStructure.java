package structure.complex.matrix3d;

import data.Bee;
import data.Metric;
import reader.best.AllLinesNIOEdgesPrimitiveReader;
import structure.common.IStructure;
import structure.complex.matrix3d.compose.Matrix3DSpace;
import util.CollisionUtil;
import util.WorldUtil;

import java.util.Date;
import java.util.LinkedList;

public class Matrix3DStructure implements IStructure {

    private Matrix3DSpace matrix;
    private AllLinesNIOEdgesPrimitiveReader reader;

    @Override
    public Metric craft() {

        Metric metric = new Metric();

        matrix = new Matrix3DSpace(reader.getMaxBound(), reader.getMinBound());

        return metric.consume();
    }

    @Override
    public Metric read() {

        Metric metric = new Metric();

        reader = new AllLinesNIOEdgesPrimitiveReader();
        reader.load();

        return metric.consume();
    }

    @Override
    public Metric insert() {

        Metric metric = new Metric();

        for (int i = 0; i < reader.getBees().length; ++i) {

            Bee bee = reader.getBees()[i];
            int x = (int) ((bee.getX() - reader.getMinBound().getX()) * 100000 / 70.72);
            int y = (int) ((bee.getY() - reader.getMinBound().getY()) * 100000 / 70.72);
            int z = (int) ((bee.getZ() - reader.getMinBound().getZ()) / 70.72);

            if (x > 0) {
                x--;
            }
            if (y > 0) {
                y--;
            }
            if (z > 0) {
                z--;
            }
            if (matrix.get(x, y, z) == null) {
                matrix.insert(x, y, z);
                matrix.get(x, y, z).add(bee);
            } else {
                matrix.get(x, y, z).push(bee);
            }
        }

        System.out.println("[MATRIX3D] Tamaño de la Matriz 3D (" + matrix.getSizeX() * matrix.getSizeY() * matrix.getSizeZ() + ")");
        return metric.consume();
    }

    @Override
    public Metric detect() {

        LinkedList<Bee> collide = new LinkedList<Bee>();
        Metric metric = new Metric();

        for (int x = 0; x < matrix.getSizeX(); x++)
            for (int y = 0; y < matrix.getSizeY(); y++)
                for (int z = 0; z < matrix.getSizeZ(); z++) {

                    if (matrix.get(x, y, z) == null)
                        continue;

                    if (matrix.get(x, y, z).size() > 1)
                        for (Bee bee : matrix.get(x, y, z))
                            collide.add(bee);

                }

        System.out.println("[MATRIX3D] Colisiones posibles: " + collide.size());
        return metric.consume();
    }

    @Override
    public Metric save() {
        return null;
    }

}
