package reader;

import data.Bee;
import reader.common.IReader;

import java.util.LinkedList;

public class VoidReader implements IReader {

    @Override
    public void load() {
    }

    @Override
    public LinkedList<Bee> getBees() {
        return null;
    }

}
