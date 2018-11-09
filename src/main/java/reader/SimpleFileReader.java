package reader;

import data.Bee;
import config.Config;
import reader.common.IReader;

import java.io.*;
import java.util.LinkedList;

public class SimpleFileReader implements IReader {

    private LinkedList<Bee> bees = new LinkedList<Bee>();

    @Override
    public void load() {

        final String PATH = Config.FILES.get("input_path");

        FileReader READER;
        BufferedReader BUFFER;
        String[] coords;

        try {

            READER = new FileReader(PATH);
            BUFFER = new BufferedReader(READER);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return;

        }

        try {

            String sbee;
            while ((sbee = BUFFER.readLine()) != null) {

                coords = sbee.split(",");

                double x = Double.valueOf(coords[0]);
                double y = Double.valueOf(coords[1]);
                double z = Double.valueOf(coords[2]);

                bees.add(new Bee(x, y, z));

            }

        } catch (IOException e) {

            e.printStackTrace();
            return;

        }

        System.out.println("[LECTÓR]: Se han encontrado " + bees.size() + " abejas.");
        return;

    }

    public LinkedList<Bee> getBees() {
        return bees;
    }

}

