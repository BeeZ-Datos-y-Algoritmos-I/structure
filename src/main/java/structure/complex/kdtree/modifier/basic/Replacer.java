package structure.complex.kdtree.modifier.basic;

public class Replacer<T> extends BaseModify<T> {

    public Replacer(T value) {
        super(value);
    }

    public T edit(T current) {
        return this.value;
    }

}
