package reader.type;

import reader.*;
import reader.best.AllLinesNIODequeReader;
import reader.best.AllLinesNIOEdgesPrimitiveReader;
import reader.common.IPrimitiveReader;
import reader.common.IReader;

public enum ReaderType {

    ALLBYTESNIOREADER(new AllBytesNIOReader()),
    ALLLINESNIOREADER(new AllLinesNIOReader()),
    ALLLINESNIOEDGESREADER(new AllLinesNIOEdgesReader()),
    ALLLINESNIOMETERSEDGESREADER(new AllLinesNIOMetersEdgesReader()),

    ALLLINESNIODEQUEREADER(new AllLinesNIODequeReader()),
    ALLLINESNIOEDGESPRIMITIVEREADER(new AllLinesNIOEdgesPrimitiveReader()),


    SIMPLEFILEREADER(new SimpleFileReader()),
    VOIDREADER(new VoidReader()),

    ;

    private IReader reader;
    private IPrimitiveReader primitiveReader;

    private boolean primitive = false;

    ReaderType(IReader reader) {

        this.reader = reader;
        this.primitive = false;

    }

    ReaderType(IPrimitiveReader reader) {

        this.primitiveReader = reader;
        this.primitive = true;

    }

    public IReader getReader() {
        return reader;
    }

}
