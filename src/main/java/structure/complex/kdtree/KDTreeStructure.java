package structure.complex.kdtree;

import data.Bee;
import data.Metric;
import reader.best.AllLinesNIOPrimitiveReader;
import reader.common.IPrimitiveReader;
import structure.complex.kdtree.element.KDTree;
import structure.common.IStructure;
import util.CollisionUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class KDTreeStructure implements IStructure {

    private KDTree<Bee> tree;
    private IPrimitiveReader reader;

    @Override
    public Metric craft() {

        Metric metric = new Metric();

        tree = new KDTree<Bee>(3);
        return metric.consume();

    }

    @Override
    public Metric read() {

        Metric metric = new Metric();

        reader = new AllLinesNIOPrimitiveReader();
        reader.load();

        return metric.consume();

    }

    @Override
    public Metric insert() {

        Metric metric = new Metric();

        for (Bee bee : reader.getBees())
            tree.insert(bee.toCartesianArr3D(), bee);

        System.out.println("[KDTREE] Tamaño del KDTree (" + tree.size() + ")");
        return metric.consume();

    }

    @Override
    public Metric detect() {

        LinkedList<Bee> collide = new LinkedList<Bee>();
        Metric metric = new Metric();

        for (Bee bee : reader.getBees()) {

            List<Bee> near = tree.nearest(bee.toCartesianArr3D(), 2);

            if (CollisionUtil.check2DCollision(bee, near.get(0)))
                collide.add(bee);

        }

        System.out.println("[KDTREE] Colisiones posibles: " + collide.size());
        return metric.consume();
    }

    @Override
    public Metric save() {
        return null;
    }

}
