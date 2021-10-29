package model;

public class Category implements Comparable<Category> {
	
	//Attribute	
	private double value;
	
	//Relation
	private Player player;
	

	public Category(Player player, double value) {
		this.player = player;
		this.value = value;
	}
	

	public double getValue() {
		return value;
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public int compareTo(Category o) {
		int number = 0;
		if (value>o.getValue()) {
			number = 1;
		}else if (value <o.getValue()) {
			number = -1;
		}else if (value == o.getValue()){
			number = 0;
		}
		
		return number;
	}
}
