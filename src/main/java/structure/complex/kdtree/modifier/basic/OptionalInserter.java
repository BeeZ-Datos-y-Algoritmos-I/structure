package structure.complex.kdtree.modifier.basic;

public class OptionalInserter<T> extends BaseModify<T> {

    public OptionalInserter(T val) {
        super(val);
    }

    public T edit(T current) {
        return (current == null) ? this.value : current;
    }

}
