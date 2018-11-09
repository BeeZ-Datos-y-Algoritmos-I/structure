package reader.best;

import config.Config;
import data.Bee;
import reader.common.IReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class AllLinesNIODequeReader implements IReader<ArrayDeque<Bee>> {

    private ArrayDeque<Bee> bees = new ArrayDeque<Bee>();

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

        for (String sbee : lines) {

            coords = sbee.split(",");

            double x = Double.valueOf(coords[0]);
            double y = Double.valueOf(coords[1]);
            double z = Double.valueOf(coords[2]);

            bees.add(new Bee(x, y, z));

        }

        System.out.println("[LECTÓR]: Se han encontrado " + bees.size() + " abejas.");
        return;

    }

    public ArrayDeque<Bee> getBees() {
        return bees;
    }

}
