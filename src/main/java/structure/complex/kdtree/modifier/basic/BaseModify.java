package structure.complex.kdtree.modifier.basic;

import main.java.structure.complex.kdtree.modifier.Modify;

public abstract class BaseModify<T> implements Modify<T> {

    final T value;

    public BaseModify(T value) {
        this.value = value;
    }

    public abstract T edit(T current);

}