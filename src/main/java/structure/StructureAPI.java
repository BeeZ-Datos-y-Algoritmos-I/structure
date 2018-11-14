package structure;

import structure.common.IStructure;
import structure.common.StructureType;

public class StructureAPI {


    public static IStructure makeStructure(StructureType type) {
        return type.getStructure();
    }


}
