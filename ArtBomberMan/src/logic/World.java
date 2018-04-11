package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class World {
	
	private Color[][] colorMatrix;
	
	private Player playerOne;
			
	private ArrayList<Player> players;
	
	private static final int DIM_WORLD=50 ;
	
	
	
	public World(Player playerOne , ArrayList<Player> players) {
		colorMatrix=new Color[DIM_WORLD][DIM_WORLD];
		for(int i=0;i<DIM_WORLD;i++) {
			for(int j=0;j<DIM_WORLD;j++) {
				colorMatrix[i][j]=Color.GREY;
			}
		}
		this.playerOne=new Player(playerOne);
		players=new ArrayList<Player>();
		for(int i=0;i<players.size();i++){
			this.players.add(new Player(players.get(i)));
		}
	}
	
	public World(ArrayList<Player> players){
		colorMatrix=new Color[DIM_WORLD][DIM_WORLD];
		for(int i=0;i<DIM_WORLD;i++) {
			for(int j=0;j<DIM_WORLD;j++) {
				colorMatrix[i][j]=Color.GREY;
			}
		}
		this.players=new ArrayList<>(players);
		
	}

	public boolean moveUp(Player p) {
		return false;
	}

	public boolean moveDown(Player p) {
		return false;
	}

	public boolean moveLeft(Player p) {
		return false;
	}

	public boolean moveRight(Player p) {
		return false;
	}

	public boolean placeBomb(Player p) {
		return false;
	}
	
	public void blowUpBomb(Player p) {

	}

	public boolean checkVictory() {
		return false;
	}

	public boolean checkPosition() {
		return false;
	}

	

}
