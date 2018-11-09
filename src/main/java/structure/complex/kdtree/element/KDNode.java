package structure.complex.kdtree.element;

import main.java.structure.complex.kdtree.modifier.Modify;
import main.java.structure.complex.kdtree.modifier.Verify;
import structure.complex.kdtree.modifier.collections.NearestNeighborList;
import structure.complex.kdtree.point.HPoint;
import structure.complex.kdtree.point.HRect;

import java.io.Serializable;
import java.util.List;

class KDNode<T> implements Serializable {

    protected HPoint k;
    protected T v;
    protected KDNode<T> left, right;
    protected boolean deleted;

    protected static <T> int edit(HPoint key, Modify<T> editor, KDNode<T> t, int lev, int K) {

        KDNode<T> next_node = null;
        int next_lev = (lev + 1) % K;

        synchronized (t) {
            if (key.equals(t.k)) {
                boolean was_deleted = t.deleted;
                t.v = editor.edit(t.deleted ? null : t.v);
                t.deleted = (t.v == null);

                if (t.deleted == was_deleted) {
                    // if I was and still am deleted or was and still am alive
                    return 0;
                } else if (was_deleted) {
                    // if I was deleted => I am now undeleted
                    return 1;
                }
                // I was not deleted, but I am now deleted
                return -1;
            } else if (key.xyzCoord[lev] > t.k.xyzCoord[lev]) {
                next_node = t.right;
                if (next_node == null) {
                    t.right = create(key, editor);
                    return t.right.deleted ? 0 : 1;
                }
            } else {
                next_node = t.left;
                if (next_node == null) {
                    t.left = create(key, editor);
                    return t.left.deleted ? 0 : 1;
                }
            }
        }

        return edit(key, editor, next_node, next_lev, K);
    }

    protected static <T> KDNode<T> create(HPoint key, Modify<T> editor) {

        KDNode<T> t = new KDNode<T>(key, editor.edit(null));

        if (t.v == null)
            t.deleted = true;

        return t;
    }

    protected static <T> boolean del(KDNode<T> t) {
        synchronized (t) {
            if (!t.deleted) {
                t.deleted = true;
                return true;
            }
        }
        return false;
    }

    protected static <T> KDNode<T> srch(HPoint key, KDNode<T> t, int K) {

        for (int lev = 0; t != null; lev = (lev + 1) % K)
            if (!t.deleted && key.equals(t.k))
                return t;
            else if (key.xyzCoord[lev] > t.k.xyzCoord[lev])
                t = t.right;
            else
                t = t.left;

        return null;
    }

    protected static <T> void rsearch(HPoint lowk, HPoint uppk, KDNode<T> t, int lev, int K, List<KDNode<T>> v) {

        if (t == null) return;

        if (lowk.xyzCoord[lev] <= t.k.xyzCoord[lev])
            rsearch(lowk, uppk, t.left, (lev + 1) % K, K, v);

        if (!t.deleted) {

            int j = 0;

            while (j < K && lowk.xyzCoord[j] <= t.k.xyzCoord[j] && uppk.xyzCoord[j] >= t.k.xyzCoord[j])
                j++;

            if (j == K)
                v.add(t);

        }

        if (uppk.xyzCoord[lev] > t.k.xyzCoord[lev])
            rsearch(lowk, uppk, t.right, (lev + 1) % K, K, v);

    }

    protected static <T> void nnbr(KDNode<T> kd, HPoint target, HRect hr,
                                   double max_dist_sqd, int lev, int K,
                                   NearestNeighborList<KDNode<T>> nnl,
                                   Verify<T> checker,
                                   long timeout) {

        if (kd == null)
            return;

        if ((timeout > 0) && (timeout < System.currentTimeMillis()))
            return;

        int s = lev % K;
        HPoint pivot = kd.k;
        double pivot_to_target = HPoint.sqrdist(pivot, target);

        HRect left_hr = hr;
        HRect right_hr = (HRect) hr.clone();
        left_hr.max.xyzCoord[s] = pivot.xyzCoord[s];
        right_hr.min.xyzCoord[s] = pivot.xyzCoord[s];

        boolean target_in_left = target.xyzCoord[s] < pivot.xyzCoord[s];

        KDNode<T> nearer_kd;
        HRect nearer_hr;
        KDNode<T> further_kd;
        HRect further_hr;

        if (target_in_left) {
            nearer_kd = kd.left;
            nearer_hr = left_hr;
            further_kd = kd.right;
            further_hr = right_hr;
        } else {
            nearer_kd = kd.right;
            nearer_hr = right_hr;
            further_kd = kd.left;
            further_hr = left_hr;
        }

        nnbr(nearer_kd, target, nearer_hr, max_dist_sqd, lev + 1, K, nnl, checker, timeout);

        KDNode<T> nearest = nnl.getHighest();
        double dist_sqd;

        if (!nnl.isCapacityReached()) {
            dist_sqd = Double.MAX_VALUE;
        } else {
            dist_sqd = nnl.getMaxPriority();
        }

        max_dist_sqd = Math.min(max_dist_sqd, dist_sqd);

        HPoint closest = further_hr.closest(target);
        if (HPoint.sqrdist(closest, target) < max_dist_sqd) {
            if (pivot_to_target < dist_sqd) {
                nearest = kd;
                dist_sqd = pivot_to_target;
                if (!kd.deleted && ((checker == null) || checker.verify(kd.v))) {
                    nnl.insert(kd, dist_sqd);
                }
                if (nnl.isCapacityReached()) {
                    max_dist_sqd = nnl.getMaxPriority();
                } else {
                    max_dist_sqd = Double.MAX_VALUE;
                }
            }

            // 10.2 Recursively call Nearest Neighbor with parameters
            //      (further-kd, target, further-hr, max-dist_sqd),
            //      storing results in temp-nearest and temp-dist-sqd
            nnbr(further_kd, target, further_hr, max_dist_sqd, lev + 1, K, nnl, checker, timeout);
        }
    }


    // constructor is used only by class; other methods are static
    private KDNode(HPoint key, T val) {

        k = key;
        v = val;
        left = null;
        right = null;
        deleted = false;
    }

    protected String toString(int depth) {
        String s = k + "  " + v + (deleted ? "*" : "");

        if (left != null)
            s = s + "\n" + pad(depth) + "L " + left.toString(depth + 1);

        if (right != null)
            s = s + "\n" + pad(depth) + "R " + right.toString(depth + 1);

        return s;
    }

    private static String pad(int n) {

        String pad = "";

        for (int i = 0; i < n; ++i)
            pad += " ";

        return pad;
    }

    private static void hrcopy(HRect hr_src, HRect hr_dst) {
        hpcopy(hr_src.min, hr_dst.min);
        hpcopy(hr_src.max, hr_dst.max);
    }

    private static void hpcopy(HPoint hp_src, HPoint hp_dst) {
        for (int i = 0; i < hp_dst.xyzCoord.length; ++i)
            hp_dst.xyzCoord[i] = hp_src.xyzCoord[i];
    }
}
