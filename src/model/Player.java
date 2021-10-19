package model;
import java.time.LocalDate;
import java.time.Period;

public class Player implements Comparable<Player>{

    private final String name;
    private String actualTeam;
    private final LocalDate birthdate;
    private double pointsPerGame, reboundsPerGame, assistsPerGame, stealsPerGame, blocksPerGame;

    
    
    public Player(String name, String actualTeam, LocalDate birthdate) {
        this.name = name;
        this.actualTeam = actualTeam;
        this.birthdate = birthdate;
    }
    public Player(String name, String actualTeam, LocalDate birthdate, double pointsPerGame, double reboundsPerGame, double assistsPerGame, double stealsPerGame, double blocksPerGame) {
        this.name = name;
        this.actualTeam = actualTeam;
        this.birthdate = birthdate;
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
        Period period = Period.between(birthdate, LocalDate.now());
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
    
    public LocalDate getBirthdate() {
    	return birthdate;
    }

    // Setters
    public void setActualTeam(String actualTeam) {
        this.actualTeam = actualTeam;
    }
	@Override
	public int compareTo(Player o) {
		return name.compareTo(o.getName());
	}
	
	public String toString() {
		return "Name: " + name + ". Team: " + actualTeam;
	}

}
