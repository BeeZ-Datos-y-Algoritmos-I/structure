package util;

import config.Config;
import javafx.geometry.Point3D;
import reader.common.IReader;
import structure.common.StructureType;

public class ConfigUtil {

    public static final double WORLD_REFERENCE_X = -75.558056;
    public static final double WORLD_REFERENCE_Y = 6.331944;
    public static final double WORLD_REFERENCE_Z = 0;

    public static Point3D REFERENCE_POINT = new Point3D(WORLD_REFERENCE_X,
            WORLD_REFERENCE_Y,
            WORLD_REFERENCE_Z);

    public static StructureType getStructureType() {

        StructureType type = StructureType.valueOf(Config.ANALYZER.get("structure"));
        return type;

    }

    public static IReader getReader() {

        try {

            Class<? extends IReader> clazz = Class.forName("reader." + Config.ANALYZER.get("reader")).asSubclass(IReader.class);
            IReader reader = clazz.newInstance();

            return reader;

        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] El lector configurado de nombre " + Config.ANALYZER.get("reader") + " no ha sido encontrado!");
            System.out.println("[ERROR] StackTrace ::");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("[ERROR] Ha ocurrido un error al instanciar el lector.");
            System.out.println("[ERROR] StackTrace ::");
            e.printStackTrace();
        }

        throw new RuntimeException("[ERROR] Ha ocurrido un problema desconocido durante la instanciación del lector.");

    }

}
