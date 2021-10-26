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
	private BinaryTree<Category> playersByPoints;
	private BinaryTree<Category> playersByRebounds;
	private BinaryTree<Category> playersByAssists;
	private BinaryTree<Category> playersBySteals;
    private ArrayList<Player> playersInList; // For testing purposes only. Players will not be stored on a list.
    
    
    public Database() {
    	players = new BinaryTree<>();
    	playersByPoints = new BinaryTree<>();
    	playersByRebounds  = new BinaryTree<>();
    	playersByAssists = new BinaryTree<>();
    	playersBySteals = new BinaryTree<>();
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
    
    
    //Method to create the 4 binary trees to efficient searches
    public void createTreesToSearchByCategory() {    	
    	for (int i=0;i<4;i++) {//4 binaryTree
    		for (int j=0;j<playersInList.size();j++) {
    			
    			Category temp = null;
    			
    			switch(i) {
        		case 0:
        			temp = new Category(playersInList.get(j), playersInList.get(j).getPointsPerGame());
        			playersByPoints.createNode(temp);
        			break;
        			
        		case 1:
        			temp = new Category(playersInList.get(j), playersInList.get(j).getReboundsPerGame());
        			playersByRebounds.createNode(temp);
        			break;
        			
        		case 2:
        			temp = new Category(playersInList.get(j), playersInList.get(j).getAssistsPerGame());
        			playersByAssists.createNode(temp);
        			break;
        			
        		case 3:
        			temp = new Category(playersInList.get(j), playersInList.get(j).getStealsPerGame());
        			playersBySteals.createNode(temp);
        			break;
        		}
    		}
    	
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

    public Player findPlayer(String player) {    
    	return findPlayer(players.getRoot(), player); 
    }

    private Player findPlayer(TreeNode<Player> current, String name) {
    	Player findPlayer = null;
    	
    	if (current!=null) {   		
    		if (name.toLowerCase().compareTo(current.getValue().getName().toLowerCase())==0) {
        		findPlayer = current.getValue();    		
        		
        	}else if (name.toLowerCase().compareTo(current.getValue().getName().toLowerCase())>0) {//Si es mayor
        		return findPlayer(current.getRight(),name);
        		
        	}else if (name.toLowerCase().compareTo(current.getValue().getName().toLowerCase())<0){//Si es menor
        		return findPlayer(current.getLeft(),name);
        	}
    	}    
    	return findPlayer;    	
    }
    /*
    
    public ArrayList<Player> findPlayerByRank(String category, String info){
    	String [] temp = info.split(";");
    	double start = Double.parseDouble(temp[0].substring(1, temp[0].length()));
    	double end = Double.parseDouble(temp[1].substring(1, temp[1].length()));
    	ArrayList<Player> tempList = new ArrayList<>();
    	
    	if (category.equals("Points per game")) {
    	
    		
    	}else if (category.equals("Rebounds per game")) {
    		
    	}else if (category.equals("Assists per Game")) {
    		
    	}else if (category.equals("Steals per game")) {
    		
    	}
    	
    	    	
    	return tempList;
   
    }
    
    
    private ArrayList<Player> findPlayerByRank(BinaryTree<Category> players, TreeNode<Double> value,double start, double end){
    	ArrayList<Player> tempList = new ArrayList<>();
    	if (start>players.getRoot().getValue().getValue()) {
    		TreeNode<Category> current = players.getRoot().getRight();
    		while (current!=null && current.getValue().getValue()>=start && current.getValue().getValue()<=end){
    			tempList.add(current.getValue().getPlayer());
    			
    			
    			
    		}
    	}
    	
    	return tempList;    	
    }
    */
    
    public ArrayList<Player> tempBiggerList = new ArrayList<>();
    public ArrayList<Player> tempSmallerList = new ArrayList<>();
    
    public ArrayList<Player> findBiggerThan(String category, String info) {    	
    	tempBiggerList.clear();
    	if (category.equals("Points per game")) {
    		findBiggerThan(playersByPoints.getRoot(), Double.parseDouble(info.substring(1, info.length())));
    		
    	}else if (category.equals("Rebounds per game")) {
    		findBiggerThan(playersByRebounds.getRoot(), Double.parseDouble(info.substring(1, info.length())));
    		
    	}else if (category.equals("Assists per Game")) {
    		findBiggerThan(playersByAssists.getRoot(), Double.parseDouble(info.substring(1, info.length())));
    		
    	}else if (category.equals("Steals per game")) {
    		findBiggerThan(playersBySteals.getRoot(), Double.parseDouble(info.substring(1, info.length())));
    		
    	}
    	
    	return tempBiggerList;
    }
    
    private void findBiggerThan(TreeNode<Category> current, double value) {    	
    	if (current!=null) {
    		
        	if (current.getValue().getValue()>=value) {
        		tempBiggerList.add(current.getValue().getPlayer());        		
        		findBiggerThan(current.getRight(),value);   		
        		findBiggerThan(current.getLeft(),value);    		
        	}else if (current.getValue().getValue()<=value) {
        		findBiggerThan(current.getRight(),value);        		
        	}
    	}
    
    	
    }

    public ArrayList<Player> findSmallerThan(String category, String info) {    	
    	tempSmallerList.clear();
    	if (category.equals("Points per game")) {
    		findSmallerThan(playersByPoints.getRoot(), Double.parseDouble(info.substring(1, info.length())));

    	}else if (category.equals("Rebounds per game")) {
    		findSmallerThan(playersByRebounds.getRoot(), Double.parseDouble(info.substring(1, info.length())));

    	}else if (category.equals("Assists per game")) {
    		findSmallerThan(playersByAssists.getRoot(), Double.parseDouble(info.substring(1, info.length())));    		

    	}else if (category.equals("Steals per game")) {
    		findSmallerThan(playersBySteals.getRoot(), Double.parseDouble(info.substring(1, info.length())));
    	}

    	return tempSmallerList ;
    }

    private void findSmallerThan(TreeNode<Category> current, double value) {   
    	if (current!=null) {
    		
    	 	if (current.getValue().getValue()<=value) {
    	 		tempSmallerList.add(current.getValue().getPlayer());

    	 		findSmallerThan(current.getLeft(),value); 
    	 		findSmallerThan(current.getRight(),value);    	 		      		      				
        	}else if (current.getValue().getValue()>=value) {
        		findSmallerThan(current.getLeft(),value);
        	}
    	}   
    }
    
    public Player findPlayerByCategory(String category, String info) {
      	Player findPlayer = null;
    	if (category.equals("Points per game")) {
    		findPlayer = findPlayerByCategory(playersByPoints.getRoot(), Double.parseDouble(info));
    		
    	}else if (category.equals("Rebounds per game")) {
    		findPlayer = findPlayerByCategory(playersByRebounds.getRoot(), Double.parseDouble(info));
    		
    	}else if (category.equals("Assists per game")) {
    		findPlayer = findPlayerByCategory(playersByAssists.getRoot(), Double.parseDouble(info));
    		
    	}else if (category.equals("Steals per game")) {
    		findPlayer = findPlayerByCategory(playersBySteals.getRoot(), Double.parseDouble(info));
    		
    	} 	
    	    	
    	return findPlayer;
    }
    
    private Player findPlayerByCategory(TreeNode<Category> current, double value) {    	
    	Player findPlayer = null;
    	if (current!=null) {
    		if (value == current.getValue().getValue()) {
    			findPlayer = current.getValue().getPlayer(); 
    			
    		}else if (value > current.getValue().getValue()) {
    			return findPlayerByCategory(current.getRight(),value);
    			
    		}else if (value < current.getValue().getValue()) {
    			return findPlayerByCategory(current.getLeft(),value);
    		}
    	}
    	
    	return findPlayer;   
    }
        
    public BinaryTree<Player> getBinaryTreePlayers(){
    	return players;
    }
    
    public BinaryTree<Category> getPlayersByPoints() {
		return playersByPoints;
	}

	public BinaryTree<Category> getPlayersByRebounds() {
		return playersByRebounds;
	}

	public BinaryTree<Category> getPlayersByAssists() {
		return playersByAssists;
	}

	public BinaryTree<Category> getPlayersBySteals() {
		return playersBySteals;
	}

	public ArrayList<Player> getPlayersInList() {
        return playersInList;
    }

    
    
}

