package console;

import config.Config;
import reader.common.IReader;
import structure.common.StructureType;
import util.ConfigUtil;
import util.FileUtil;

import java.io.IOException;
import java.util.Date;

public class Console {

    public static StructureType structure;

    public static void start(String... args) throws IOException {

        System.out.println("[INFORMACIÓN]: Inicializado el sistema de analisis por estructura de datos, se leerá el archivo structure.ini");
        System.out.println("[ACCIÓN]: Leyendo el archivo structure.ini");

        Config.load(args);
        System.out.println("[ACCIÓN]: Leyendo el archivo de entrada " + Config.FILES.get("input_path") + "...");
        System.out.println("[ACCIÓN]: Se ha pre-cargado y se ejecutará con [threads=" + Config.VALUES.get("number_of_threads") + ", lines= " + (FileUtil.countLines(Config.FILES.get("input_path")) + 1) + "].");

        structure = ConfigUtil.getStructureType();
        System.out.println("[ACCIÓN]: Se utilizará la estructura de datos " + structure.name() + ".");

        Date date = makeDate();

        System.out.println("[LECTÓR]: La lectura ha tardado: " + structure.getStructure().read() + " ms");
        System.out.println("[ANALIZADOR]: Generando archivo " + Config.FILES.get("output_path") + "...");

        System.out.println("[ESTRUCTURA]: La instanciación de la estructura ha tardado: " + structure.getStructure().craft() + " ms");
        System.out.println("[ESTRUCTURA]: La estructura ha insertado todos los datos en: " + structure.getStructure().insert() + " ms");
        System.out.println("[ESTRUCTURA]: La estructura ha detectado las posibles colisiones en: " + structure.getStructure().detect() + " ms");

        long diff = (makeDate().getTime() - date.getTime());

        System.out.println("[INFORMACIÓN]: El tiempo total que se ha tardado en cargar todo ha sido en " + diff + " ms");

    }

    public static Date makeDate() {
        return new Date();
    }

}