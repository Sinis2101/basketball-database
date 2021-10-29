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
        
    private ArrayList<Player> tempRankList = new ArrayList<>();
    private ArrayList<Player> tempBiggerList = new ArrayList<>();
    private ArrayList<Player> tempSmallerList = new ArrayList<>();
    
    
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
        createTreesToSearchByCategory(player);
    }
    
    public void deletePlayer(String player) {    
    	//System.out.println("PLAYER: "+player);
    	Player findPlayer = findPlayer(players.getRoot(), player);
    	if (findPlayer!=null) {    	
    		playersInList.remove(indexInArrayList(player));
        	players.deleteNode(players.getRoot(), findPlayer);
        	//System.out.println("LO ELIIMINA DEL ARBOL");
    	}   	    	
    }
    
    
    //Method to create the 4 binary trees to efficient searches
    public void createTreesToSearchByCategory(Player player) {  
    	if (player!=null) {    		
    		Category tempPoints = new Category(player, player.getPointsPerGame());
        	Category tempRebounds = new Category(player, player.getReboundsPerGame());
        	Category tempAssists = new Category(player, player.getAssistsPerGame());
        	Category tempSteals = new Category(player, player.getStealsPerGame());
        	
        	playersByPoints.createNode(tempPoints);
        	playersByRebounds.createNode(tempRebounds);
        	playersByAssists.createNode(tempAssists);
        	playersBySteals.createNode(tempSteals);
        	
    	}    
    }
    

    public void importPlayers(String path, String separator) throws IOException {
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
            
            line = br.readLine();
        }

        br.close();        
    }

    public Player findPlayer(String player) {    
    	return findPlayer(players.getRoot(), player); 
    }

    private Player findPlayer(TreeNode<Player> current, String name) {
    	Player findPlayer = null;
    	
    	if (current!=null) {   		
    		//System.out.println("CURRENT: "+current.getValue().getName());
    		if (name.toLowerCase().equals(current.getValue().getName().toLowerCase())) {
        		findPlayer = current.getValue();    		
        		//System.out.println("founded");
        		
        	}else if (name.toLowerCase().compareTo(current.getValue().getName().toLowerCase())>0) {//Si es mayor
        		return findPlayer(current.getRight(),name);
        		
        	}else if (name.toLowerCase().compareTo(current.getValue().getName().toLowerCase())<0){//Si es menor
        		return findPlayer(current.getLeft(),name);
        	}
    	}    
    	return findPlayer;    	
    }
    
    public int indexInArrayList(String name) {   	
    	int index = 0;
    	for (int i=0;i<playersInList.size();i++) {    		
    		if (playersInList.get(i).getName().equalsIgnoreCase(name)) {
    			index = i;
    		}
    	}
    	return index;    	
    }
    
    public ArrayList<Player> findPlayersByTeam(String team){
    	return findPlayersByTeam(playersInList, team);    	
    }
    
    private ArrayList<Player> findPlayersByTeam(ArrayList<Player> list, String team){
    	ArrayList<Player> listPlayers = new ArrayList<>();
    	
    	for (int i=0;i<list.size();i++) {
    		if (list.get(i).getActualTeam().equalsIgnoreCase(team)) {
    			listPlayers.add(list.get(i));
    		}
    	}
    	
    	return listPlayers;
    }
   
    public ArrayList<Player> findPlayerByRank(String category, String info){
    	tempRankList.clear();
    	String [] temp = info.split(";");
    	String start = temp[0];
    	String end = temp[1];
    	
    	//System.out.println("start "+start);
    	//System.out.println("end "+end);
    	    	
    	tempRankList = findBiggerThan(category, start);    	
    	
    	//System.out.println("tempRankList size: "+tempRankList.size());
    	
    	findPlayerByRank(category, tempRankList,end); 
    	
    	//System.out.println("tempRankList size: "+tempRankList.size());
    	    	
    	return tempRankList;
   
    }
    
    
    private void findPlayerByRank(String category,ArrayList<Player> list,String end){
    	double finalRank = Double.parseDouble(end.substring(1,end.length()));
      	
    	if (category.equals("Points per game")) {
    		for (int i=0;i<list.size();i++) {
        		if (list.get(i).getPointsPerGame()>finalRank) {
        			tempRankList.remove(i);        			
        		}
        	}
    		
    	}else if (category.equals("Rebounds per game")) {
    		for (int i=0;i<list.size();i++) {
        		if (list.get(i).getReboundsPerGame()>finalRank) {
        			tempRankList.remove(i);
        		}
        	}
    		
    	}else if (category.equals("Assists per game")) {
    		for (int i=0;i<list.size();i++) {
        		if (list.get(i).getAssistsPerGame()>finalRank) {
        			tempRankList.remove(i);
        		}
        	}
    	
    		
    	}else if (category.equals("Steals per game")) {
    		for (int i=0;i<list.size();i++) {
        		if (list.get(i).getStealsPerGame()>finalRank) {
        			tempRankList.remove(i);
        		}
        	}
    		
    	}    	
  	
    }
    
    

    
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

