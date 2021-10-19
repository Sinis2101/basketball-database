package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import MyBinaryTree_data_structure.BinaryTree;
import MyBinaryTree_data_structure.TreeNode;

public class Database {
	
	private BinaryTree<Player> players;
    private ArrayList<Player> playersInList; // For testing purposes only. Players will not be stored on a list.
    
    
    public Database() {
    	players = new BinaryTree<>();
    	playersInList = new ArrayList<>();
    	
        // Players added for testing purposes only. Delete later.
        /*players.add(new Player("Juan Felipe Sinisterra", "Miami Heats", LocalDate.of(2001, 9,21), 0.0, 0.0,0.0,0.0,0.0));
        players.add(new Player("Tomas Ossa", "Lakers", LocalDate.of(2001, 9,21), 0.0, 0.0,0.0,0.0,0.0));
        players.add(new Player("Daniela Bonilla", "Bulls", LocalDate.of(2001, 9,21), 0.0, 0.0,0.0,0.0,0.0));*/
    }

    public void addPlayer(Player player) {
        players.createNode(player);
        playersInList.add(player);        
    }
    
    public void deletePlayer(String player) {
    	Player findPlayer = findPlayer(players.getRoot(), player);
    	if (findPlayer!=null) {    	
    		playersInList.remove(findPlayer);
        	players.deleteNode(players.getRoot(), findPlayer);
    	}   	    	
    }

    public void importPlayers(String path, String separator) throws IOException {
        //int importAmount = 0;
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
            //importAmount++;

            line = br.readLine();
        }

        br.close();

        //return importAmount;
    }

    public Player findPlayer(String name) {
        return findPlayer(players.getRoot(), name);
    }

    private Player findPlayer(TreeNode<Player> current, String name) {
    	Player findPlayer = null;
    	if (current!=null) {
    		
    		if (name.equals(current.getValue().getName())) {
        		findPlayer = current.getValue();    		
        		
        	}else if (name.compareTo(current.getValue().getName())>0) {//Si es mayor
        		return findPlayer(current.getRight(),name);
        	}else if (name.compareTo(current.getValue().getName())<0){//Si es menor
        		return findPlayer(current.getLeft(),name);
        	}
    	}    
    	return findPlayer;    	
    }
    
    public BinaryTree<Player> getBinaryTreePlayers(){
    	return players;
    }

    public ArrayList<Player> getPlayersInList() {
        return playersInList;
    }

}
