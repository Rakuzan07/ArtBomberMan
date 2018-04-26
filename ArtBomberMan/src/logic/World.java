package logic;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class World {

	private Color[][] colorMatrix;

	private Player playerOne;

	private ArrayList<Player> players;

	private static final int DIM_WORLD = 50;

	private static final double VICTORY_PERCENTAGE = 0.4;

	private int dimWorld;

	private Player winningPlayer;

	private Semaphore victory = new Semaphore(1);

	public World(Player playerOne, ArrayList<Player> players) {
		dimWorld = DIM_WORLD;
		colorMatrix = new Color[dimWorld][dimWorld];
		for (int i = 0; i < dimWorld; i++) {
			for (int j = 0; j < dimWorld; j++) {
				colorMatrix[i][j] = Color.GREY;
			}
		}
		this.playerOne = playerOne;
		this.players = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			this.players.add(players.get(i));
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
		this.playerOne = playerOne;
		this.players = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			this.players.add(players.get(i));
		}
	}

	public int getDimension() {
		return dimWorld;
	}

	public void paint(Position p, Color color) {
		if (p.getX() < 0 || p.getX() > dimWorld || p.getY() < 0 || p.getY() > dimWorld)
			throw new IllegalArgumentException();
		int x = p.getX();
		int y = p.getY();
		colorMatrix[y][x] = color;
		if (y - 1 >= 0)
			colorMatrix[y - 1][x] = color;
		if (y - 1 >= 0 && x - 1 >= 0)
			colorMatrix[y - 1][x - 1] = color;
		if (y - 1 >= 0 && x + 1 < dimWorld)
			colorMatrix[y - 1][x + 1] = color;
		if (x - 1 >= 0)
			colorMatrix[y][x - 1] = color;
		if (x + 1 < dimWorld)
			colorMatrix[y][x + 1] = color;
		if (y + 1 < dimWorld)
			colorMatrix[y + 1][x] = color;
		if (y + 1 < dimWorld && x - 1 >= 0)
			colorMatrix[y + 1][x - 1] = color;
		if (y + 1 < dimWorld && x + 1 < dimWorld)
			colorMatrix[y + 1][x + 1] = color;
	}

	public void generateEnemy() {
		for (int i = 0; i < players.size(); i++) {
			ArrayList<Player> tempPlayers = new ArrayList<>();
			tempPlayers.add(playerOne);
			for (int j = 0; j < players.size(); j++) {
                if(i!=j) tempPlayers.add(players.get(j));
			}
			new Thread(new Enemy(players.get(i),tempPlayers)).start();
		}
	}
	
	public String toString() {
		ArrayList<Position> tempBombPosition=new ArrayList<Position>();
		tempBombPosition.addAll(playerOne.getBombPosition());
		for(int i=0;i<players.size();i++) {
			tempBombPosition.addAll(players.get(i).getBombPosition());
		}
		StringBuilder sb=new StringBuilder(1000);
		for(int i=0;i<dimWorld;i++) {
			for(int j=0;j<dimWorld;j++) {
				if(checkPlayer(j,i)) sb.append("P");
				else if(checkBomb(tempBombPosition,j,i)) sb.append("B");
				else sb.append(colorMatrix[i][j].toString());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private boolean checkBomb(ArrayList<Position> bomb , int x ,int y) {
		for(int i=0;i<bomb.size();i++) {
			if(bomb.get(i).getX()==x&&bomb.get(i).getY()==y) return true;
		}
		return false;
	}
	
	private boolean checkPlayer(int x , int y) {
		if(playerOne.getX()==x&&playerOne.getY()==y) return true;
		for(int i=0;i<players.size();i++) {
			if(players.get(i).getX()==x&&players.get(i).getY()==y) return true;
		}
		return false;
	}

	public void startVictoryControl() throws InterruptedException {
		victory.acquire();
	}

	public boolean checkVictory(Player p) {
		if (p != playerOne && !players.contains(p))
			throw new IllegalArgumentException("The player does not exist in this world");
		int cont = 0;
		for (int i = 0; i < dimWorld; i++) {
			for (int j = 0; j < dimWorld; j++) {
				if (colorMatrix[i][j] == p.getColor())
					cont++;
			}
		}
		return ((double) cont / dimWorld) >= VICTORY_PERCENTAGE;
	}

	public void endVictoryControl() {
		victory.release();
	}

	public void setWinningPlayer(Player winningPlayer) {
		this.winningPlayer = winningPlayer;
	}
	
	public Color[][] getColorMatrix(){
		return colorMatrix;
	}
	
	public Color getDefaultColor() {
		return Color.GREY;
	}

	

}
