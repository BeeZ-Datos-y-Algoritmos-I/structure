package structure.complex.kdtree.modifier.basic;

public class Inserter<T> extends BaseModify<T> {

    public Inserter(T value) {
        super(value);
    }

    public T edit(T current) {

        if (current == null)
            return this.value;

        throw new IllegalArgumentException();
    }

}