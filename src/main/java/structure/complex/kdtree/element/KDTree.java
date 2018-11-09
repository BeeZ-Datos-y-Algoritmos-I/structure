package structure.complex.kdtree.element;

import main.java.structure.complex.kdtree.modifier.Modify;
import main.java.structure.complex.kdtree.modifier.Verify;
import structure.complex.kdtree.modifier.basic.DistanceCalculatorType;
import structure.complex.kdtree.modifier.basic.Inserter;
import structure.complex.kdtree.modifier.collections.NearestNeighborList;
import structure.complex.kdtree.point.HPoint;
import structure.complex.kdtree.point.HRect;

import java.io.Serializable;

import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

public class KDTree<T> implements Serializable {

    final long m_timeout;

    final private int m_K;

    private KDNode<T> m_root;

    private int m_count;

    public KDTree(int k) {
        this(k, 0);
    }

    public KDTree(int k, long timeout) {
        this.m_timeout = timeout;
        m_K = k;
        m_root = null;
    }


    /**
     * Insert a node in a KD-tree.  Uses algorithm translated from 352.ins.c of
     *
     * <PRE>
     * &*064;Book{GonnetBaezaYates1991,
     * author =    {G.H. Gonnet and R. Baeza-Yates},
     * title =     {Handbook of Algorithms and Data Structures},
     * publisher = {Addison-Wesley},
     * year =      {1991}
     * }
     * </PRE>
     *
     * @param key   key for KD-tree node
     * @param value value at that key
     */
    public void insert(double[] key, T value) {
        this.edit(key, new Inserter<T>(value));
    }

    /**
     * Edit a node in a KD-tree
     *
     * @param key    key for KD-tree node
     * @param editor object to edit the value at that key
     */

    public void edit(double[] key, Modify<T> editor) {

        if (key.length != m_K)
            throw new IllegalStateException("No se puede generar debido a la incongruencia del tamaño.");

        synchronized (this) {
            if (null == m_root) {
                m_root = KDNode.create(new HPoint(key), editor);
                m_count = m_root.deleted ? 0 : 1;
                return;
            }
        }

        m_count += KDNode.edit(new HPoint(key), editor, m_root, 0, m_K);
    }

    /**
     * Find  KD-tree node whose key is identical to key.  Uses algorithm
     * translated from 352.srch.c of Gonnet & Baeza-Yates.
     *
     * @param key key for KD-tree node
     * @return object at key, or null if not found
     */
    public T search(double[] key) {

        if (key.length != m_K)
            throw new IllegalStateException("No se puede generar debido a la incongruencia del tamaño.");

        KDNode<T> kd = KDNode.srch(new HPoint(key), m_root, m_K);

        return kd == null ? null : kd.v;

    }


    public void delete(double[] key) {
        delete(key, false);
    }

    /**
     * Delete a node from a KD-tree.  Instead of actually deleting node and
     * rebuilding tree, marks node as deleted.  Hence, it is up to the caller
     * to rebuild the tree as needed for efficiency.
     *
     * @param key      key for KD-tree node
     * @param optional if false and node not found, throw an exception
     */
    public void delete(double[] key, boolean optional) {

        if (key.length != m_K)
            throw new IllegalStateException("No se puede generar debido a la incongruencia del tamaño.");

        KDNode<T> t = KDNode.srch(new HPoint(key), m_root, m_K);

        if (t == null) {
            if (optional == false)
                throw new IllegalStateException("No se puede remover debido a que la llave no se encuentra.");
        } else {
            if (KDNode.del(t))
                m_count--;
        }

    }

    /**
     * Find KD-tree node whose key is nearest neighbor to
     * key.
     *
     * @param key key for KD-tree node
     * @return object at node nearest to key, or null on failure
     */
    public T nearest(double[] key) {
        List<T> nbrs = nearest(key, 1, null);
        return nbrs.get(0);
    }

    /**
     * Find KD-tree nodes whose keys are <i>n</i> nearest neighbors to
     * key.
     *
     * @param key key for KD-tree node
     * @param n   number of nodes to return
     * @return objects at nodes nearest to key, or null on failure
     */
    public List<T> nearest(double[] key, int n) {
        return nearest(key, n, null);
    }

    /**
     * Find KD-tree nodes whose keys are within a given Euclidean distance of
     * a given key.
     *
     * @param key  key for KD-tree node
     * @param dist Euclidean distance
     * @return objects at nodes with distance of key, or null on failure
     */
    public List<T> nearestEuclidean(double[] key, double dist) {
        return nearestDistance(key, dist, DistanceCalculatorType.EUCLIDEAN_DISTANCE);
    }


    /**
     * Find KD-tree nodes whose keys are within a given Hamming distance of
     * a given key.
     *
     * @param key key for KD-tree node
     * @return objects at nodes with distance of key, or null on failure
     */
    public List<T> nearestHamming(double[] key, double dist) {
        return nearestDistance(key, dist, DistanceCalculatorType.HAMMING_DISTANCE);
    }


    /**
     * Find KD-tree nodes whose keys are <I>n</I> nearest neighbors to
     * key. Uses algorithm above.  Neighbors are returned in ascending
     * order of distance to key.
     *
     * @param key     key for KD-tree node
     * @param n       how many neighbors to find
     * @param checker an optional object to filter matches
     * @return objects at node nearest to key, or null on failure
     * @throws IllegalArgumentException if <I>n</I> is negative or
     *                                  exceeds tree size
     */
    public List<T> nearest(double[] key, int n, Verify<T> checker) {

        if (n <= 0)
            return new LinkedList<T>();

        NearestNeighborList<KDNode<T>> nnl = getnbrs(key, n, checker);

        n = nnl.getSize();
        Stack<T> nbrs = new Stack<T>();

        for (int i = 0; i < n; ++i) {
            KDNode<T> kd = nnl.removeHighest();
            nbrs.push(kd.v);
        }

        return nbrs;
    }


    /**
     * Range search in a KD-tree.  Uses algorithm translated from
     * 352.range.c of Gonnet & Baeza-Yates.
     *
     * @param lowk lower-bounds for key
     * @param uppk upper-bounds for key
     * @return array of Objects whose keys fall in range [lowk,uppk]
     */
    public List<T> range(double[] lowk, double[] uppk) {

        if (lowk.length != uppk.length)
            throw new IllegalStateException("No se puede generar debido a la incongruencia del tamaño.");
        else if (lowk.length != m_K)
            throw new IllegalStateException("No se puede generar debido a la incongruencia del tamaño.");
        else {
            List<KDNode<T>> found = new LinkedList<KDNode<T>>();
            KDNode.rsearch(new HPoint(lowk), new HPoint(uppk),
                    m_root, 0, m_K, found);
            List<T> o = new LinkedList<T>();
            for (KDNode<T> node : found) {
                o.add(node.v);
            }
            return o;
        }

    }

    public int size() { /* added by MSL */
        return m_count;
    }

    public String toString() {
        return m_root.toString(0);
    }

    private NearestNeighborList<KDNode<T>> getnbrs(double[] key) {
        return getnbrs(key, m_count, null);
    }

    private NearestNeighborList<KDNode<T>> getnbrs(double[] key, int n, Verify<T> checker) {

        if (key.length != m_K)
            throw new IllegalStateException("No se puede generar debido a la incongruencia del tamaño.");

        NearestNeighborList<KDNode<T>> nnl = new NearestNeighborList<KDNode<T>>(n);

        // initial call is with infinite hyper-rectangle and max distance
        HRect hr = HRect.infiniteHRect(key.length);
        double max_dist_sqd = Double.MAX_VALUE;
        HPoint keyp = new HPoint(key);

        if (m_count > 0) {
            long timeout = (this.m_timeout > 0) ?
                    (System.currentTimeMillis() + this.m_timeout) :
                    0;
            KDNode.nnbr(m_root, keyp, hr, max_dist_sqd, 0, m_K, nnl, checker, timeout);
        }

        return nnl;

    }

    private List<T> nearestDistance(double[] key, double dist, DistanceCalculatorType calc) {

        NearestNeighborList<KDNode<T>> nnl = getnbrs(key);

        int n = nnl.getSize();
        Stack<T> nbrs = new Stack<T>();

        for (int i = 0; i < n; ++i) {

            KDNode<T> kd = nnl.removeHighest();
            HPoint p = kd.k;

            if (calc.getCalculator().distance(kd.k.xyzCoord, key) < dist) {
                nbrs.push(kd.v);
            }

        }

        return nbrs;
    }


}

