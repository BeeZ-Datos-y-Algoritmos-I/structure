package structure.complex.kdtree.modifier.collections;

import java.util.PriorityQueue;

public class NearestNeighborList<T> {

    private PriorityQueue<NeighborEntry<T>> m_Queue;
    private int m_Capacity = 0;

    public NearestNeighborList(int capacity) {
        m_Capacity = capacity;
        m_Queue = new java.util.PriorityQueue<NeighborEntry<T>>(m_Capacity);
    }

    public double getMaxPriority() {
        NeighborEntry p = m_Queue.peek();
        return (p == null) ? Double.POSITIVE_INFINITY : p.value;
    }

    public boolean insert(T object, double priority) {

        if (isCapacityReached()) {

            if (priority > getMaxPriority())
                return false;

            m_Queue.add(new NeighborEntry<T>(object, priority));
            m_Queue.poll();

        } else {

            m_Queue.add(new NeighborEntry<T>(object, priority));

        }

        return true;
    }

    public boolean isCapacityReached() {
        return m_Queue.size() >= m_Capacity;
    }

    public T getHighest() {
        NeighborEntry<T> p = m_Queue.peek();
        return (p == null) ? null : p.data;
    }

    public boolean isEmpty() {
        return m_Queue.size() == 0;
    }

    public int getSize() {
        return m_Queue.size();
    }

    public T removeHighest() {
        NeighborEntry<T> p = m_Queue.poll();
        return (p == null) ? null : p.data;
    }

    public static class NeighborEntry<T> implements Comparable<NeighborEntry<T>> {

        final T data;
        final double value;

        public NeighborEntry(final T data,
                             final double value) {
            this.data = data;
            this.value = value;
        }

        public int compareTo(NeighborEntry<T> t) {
            return Double.compare(t.value, this.value);
        }

    }

    ;

}
