package structure.common;

import structure.complex.kdtree.KDTreeStructure;
import structure.complex.matrix3d.Matrix3DStructure;

public enum StructureType {

    KDTREE(new KDTreeStructure()),
    MATRIX3D(new Matrix3DStructure()),

    ;

    private IStructure structure;

    StructureType(IStructure structure) {

        this.structure = structure;

    }

    public IStructure getStructure() {
        return structure;
    }

}
