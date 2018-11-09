package reader;

import data.Bee;
import config.Config;
import javafx.geometry.Point3D;
import reader.common.IReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AllLinesNIOMetersEdgesReader implements IReader {

    public static Point3D max_bound, min_bound;
    private LinkedList<Bee> bees = new LinkedList<Bee>();

    @Override
    public void load() {

        final String PATH = Config.FILES.get("input_path");

        File file = new File(PATH);
        List<String> lines = new ArrayList<>();
        String[] coords;

        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean fbee = true;
        double max_x = 0,
                min_x = 0,
                max_y = 0,
                min_y = 0,
                max_z = 0,
                min_z = 0;

        for (String sbee : lines) {

            coords = sbee.split(",");

            double x = Double.valueOf(coords[0]);
            double y = Double.valueOf(coords[1]);
            double z = Double.valueOf(coords[2]);

            Bee bee = new Bee(x, y, z);

            if (fbee) {

                fbee = false;

                max_x = bee.getX();
                min_x = bee.getX();

                max_y = bee.getY();
                min_y = bee.getY();

                max_z = bee.getZ();
                min_z = bee.getZ();

            }

            if (bee.getX() > max_x)
                max_x = bee.getX();
            else if (bee.getX() < min_x)
                min_x = bee.getX();

            if (bee.getY() > max_y)
                max_y = bee.getY();
            else if (bee.getY() < min_y)
                min_y = bee.getY();

            if (bee.getZ() > max_z)
                max_z = bee.getZ();
            else if (bee.getZ() < min_z)
                min_z = bee.getZ();

            bees.add(bee);

        }

        max_bound = new Point3D(max_x, max_y, max_z);
        min_bound = new Point3D(min_x, min_y, min_z);

        System.out.println("[LECTÓR]: Se han encontrado " + bees.size() + " abejas.");
        return;

    }

    public LinkedList<Bee> getBees() {
        return bees;
    }

}
