package reader.best;

import config.Config;
import data.Bee;
import reader.common.IPrimitiveReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class AllLinesNIOPrimitiveReader implements IPrimitiveReader {

    private Bee[] bees;

    @Override
    public void load() {

        final String PATH = Config.FILES.get("input_path");

        File file = new File(PATH);
        List<String> lines = new LinkedList<>();
        String[] coords;

        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bees = new Bee[lines.size()];

        for (int i = 0; i < lines.size(); i++) {

            coords = lines.get(i).split(",");

            double x = Double.valueOf(coords[0]);
            double y = Double.valueOf(coords[1]);
            double z = Double.valueOf(coords[2]);

            bees[i] = new Bee(x, y, z);

        }

        System.out.println("[LECTÓR]: Se han encontrado " + bees.length + " abejas.");
        return;

    }

    public Bee[] getBees() {
        return bees;
    }

}
