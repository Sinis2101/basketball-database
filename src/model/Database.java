package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class Database {

    private int playersAmount;

    public void addPlayer(Player player) {

    }

    public int importPlayers(String path, String separator) throws IOException {
        int importAmount = 0;
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();
        line = br.readLine();

        while (line != null) {
            String[] parts = line.split(separator);

            String name = parts[0];
            String actualTeam = parts[1];
            LocalDate birthday = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),Integer.parseInt(parts[4]));
            double pointsPerGame = Double.parseDouble(parts[5]);
            double reboundsPerGame = Double.parseDouble(parts[6]);
            double assistsPerGame = Double.parseDouble(parts[7]);
            double stealsPerGame = Double.parseDouble(parts[8]);
            double blocksPerGame = Double.parseDouble(parts[9]);

            Player player = new Player(name, actualTeam, birthday, pointsPerGame, reboundsPerGame, assistsPerGame, stealsPerGame, blocksPerGame);

            addPlayer(player);
            importAmount++;

            line = br.readLine();
        }

        br.close();

        return importAmount;
    }

}
