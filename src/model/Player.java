package model;
import java.time.LocalDate;
import java.time.Period;

public class Player {

    private final String name;
    private String actualTeam;
    private final LocalDate birthday;
    private double pointsPerGame, reboundsPerGame, assistsPerGame, stealsPerGame, blocksPerGame;

    public Player(String name, String actualTeam, LocalDate birthday) {
        this.name = name;
        this.actualTeam = actualTeam;
        this.birthday = birthday;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getActualTeam() {
        return actualTeam;
    }
    public int getAge() {
        Period period = Period.between(birthday, LocalDate.now());
        return period.getYears();
    }
    public double getPointsPerGame() {
        return pointsPerGame;
    }
    public double getReboundsPerGame() {
        return reboundsPerGame;
    }
    public double getAssistsPerGame() {
        return assistsPerGame;
    }
    public double getStealsPerGame() {
        return stealsPerGame;
    }
    public double getBlocksPerGame() {
        return blocksPerGame;
    }

    // Setters
    public void setActualTeam(String actualTeam) {
        this.actualTeam = actualTeam;
    }

}
