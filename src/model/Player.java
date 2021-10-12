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
    public Player(String name, String actualTeam, LocalDate birthday, double pointsPerGame, double reboundsPerGame, double assistsPerGame, double stealsPerGame, double blocksPerGame) {
        this.name = name;
        this.actualTeam = actualTeam;
        this.birthday = birthday;
        this.pointsPerGame = pointsPerGame;
        this.reboundsPerGame = reboundsPerGame;
        this.assistsPerGame = assistsPerGame;
        this.stealsPerGame = stealsPerGame;
        this.blocksPerGame = blocksPerGame;
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
