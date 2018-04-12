package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import logic.Player.Status;

public class World {

	private Color[][] colorMatrix;

	private Player playerOne;

	private ArrayList<Player> players;

	private static final int DIM_WORLD = 50;

	private int dimWorld;

	public World(Player playerOne, ArrayList<Player> players) {
		dimWorld = DIM_WORLD;
		colorMatrix = new Color[dimWorld][dimWorld];
		for (int i = 0; i < dimWorld; i++) {
			for (int j = 0; j < dimWorld; j++) {
				colorMatrix[i][j] = Color.GREY;
			}
		}
		this.playerOne = new Player(playerOne);
		players = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			this.players.add(new Player(players.get(i)));
		}
	}

	public World(Player playerOne, ArrayList<Player> players, int dimWorld) {
		this.dimWorld = dimWorld;
		colorMatrix = new Color[dimWorld][dimWorld];
		for (int i = 0; i < this.dimWorld; i++) {
			for (int j = 0; j < this.dimWorld; j++) {
				colorMatrix[i][j] = Color.GREY;
			}
		}
		this.playerOne = new Player(playerOne);
		players = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			this.players.add(new Player(players.get(i)));
		}
	}

	public void refresh() {
		if((playerOne.getState()==Status.DOWN&&playerOne.getY()+1<dimWorld)||
				(playerOne.getState()==Status.UP&&playerOne.getY()-1>=0)||
				(playerOne.getState()==Status.RIGHT&&playerOne.getX()+1<dimWorld)||
				(playerOne.getState()==Status.LEFT&&playerOne.getX()-1>=0)) {
			playerOne.permit();
			playerOne.move();
		}
       for(int i=0;i<players.size();i++) {
    	   if((players.get(i).getState()==Status.DOWN&&playerOne.getY()+1<dimWorld)||
   				(players.get(i).getState()==Status.UP&&playerOne.getY()-1>=0)||
   				(players.get(i).getState()==Status.RIGHT&&playerOne.getX()+1<dimWorld)||
   				(players.get(i).getState()==Status.LEFT&&playerOne.getX()-1>=0)) {
    		   players.get(i).permit();
    		   players.get(i).move();
   		}
       }
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
