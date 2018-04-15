package logic;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class World {

	private Color[][] colorMatrix;

	private Player playerOne;

	private ArrayList<Player> players;

	private static final int DIM_WORLD = 50;
	
	private static final double VICTORY_PERCENTAGE=0.4;

	private int dimWorld;

	private Player winningPlayer;
	
	private Semaphore victory=new Semaphore(1);
	
	public World(Player playerOne, ArrayList<Player> players) {
		dimWorld = DIM_WORLD;
		colorMatrix = new Color[dimWorld][dimWorld];
		for (int i = 0; i < dimWorld; i++) {
			for (int j = 0; j < dimWorld; j++) {
				colorMatrix[i][j] = Color.GREY;
			}
		}
		this.playerOne = playerOne;
		players = new ArrayList<Player>();
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
		players = new ArrayList<Player>();
		for (int i = 0; i < players.size(); i++) {
			this.players.add(players.get(i));
		}
	}
	
	public int getDimension(){
		return dimWorld;
	}

	public void paint(Position p , Color color){
		 if(p.getX()<0||p.getX()>dimWorld||p.getY()<0||p.getY()>dimWorld) throw new IllegalArgumentException();
		 int x =p.getX();
		 int y=p.getY();
		 colorMatrix[y][x]=color;
		 if(y-1>=0)colorMatrix[y-1][x]=color;
		 if(y-1>=0&&x-1>=0)colorMatrix[y-1][x-1]=color;
		 if(y-1>=0&&x+1<dimWorld)colorMatrix[y-1][x+1]=color;
		 if(x-1>=0)colorMatrix[y][x-1]=color;
		 if(x+1<dimWorld)colorMatrix[y][x+1]=color;
		 if(y+1<dimWorld)colorMatrix[y+1][x]=color;
		 if(y+1<dimWorld&&x-1>=0)colorMatrix[y+1][x-1]=color;
		 if(y+1<dimWorld&&x+1<dimWorld)colorMatrix[y+1][x+1]=color;
	}

	public void generateEnemy() {
		
	}
	
	public void startVictoryControl() throws InterruptedException {
		victory.acquire();
	}
	
	public boolean checkVictory(Player p) {
		if(p!=playerOne&&!players.contains(p)) throw new IllegalArgumentException("The player does not exist in this world");
		int cont=0;
		for(int i=0;i<dimWorld;i++) {
			for(int j=0;j<dimWorld;j++) {
				if(colorMatrix[i][j]==p.getColor()) cont++;
			}
		}
		return ((double)cont/dimWorld)>=VICTORY_PERCENTAGE;
	}
	
	public void endVictoryControl() {
		victory.release();
	}
	
	public void setWinningPlayer(Player winningPlayer) {
		this.winningPlayer=winningPlayer;
	}

	public boolean checkPosition() {
		return false;
	}

}
