package structure.complex.kdtree;

import data.Bee;
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
    public long craft() {

        long millis = new Date().getTime();

        tree = new KDTree<Bee>(3);
        return new Date().getTime() - millis;

    }

    @Override
    public long read() {

        long millis = new Date().getTime();

        reader = new AllLinesNIOPrimitiveReader();
        reader.load();

        return new Date().getTime() - millis;

    }

    @Override
    public long insert() {

        long millis = new Date().getTime();

        for (Bee bee : reader.getBees())
            tree.insert(bee.toCartesianArr3D(), bee);

        System.out.println("[KDTREE] Tamaño del KDTree (" + tree.size() + ")");
        return new Date().getTime() - millis;

    }

    @Override
    public long detect() {

        LinkedList<Bee> collide = new LinkedList<Bee>();
        long millis = new Date().getTime();

        long b = 0, h = 0;

        for (Bee bee : reader.getBees()) {

            long k = new Date().getTime();

            List<Bee> near = tree.nearest(bee.toCartesianArr3D(), 2);

            b += new Date().getTime() - k;

            long l = new Date().getTime();

            if (CollisionUtil.check2DCollision(bee, near.get(0)))
                collide.add(bee);

            h += new Date().getTime() - l;

        }

        System.out.println(b + "    " + h);
        System.out.println("[KDTREE] Colisiones posibles: " + collide.size());
        return new Date().getTime() - millis;
    }

    @Override
    public long save() {
        return 0;
    }

}
