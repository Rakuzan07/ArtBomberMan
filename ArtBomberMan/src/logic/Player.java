package logic;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Player {
	
	private static final int TANK_VALUE=4;
	
	private int inkTank , numBomb;
	
	private Color color;
	
	private Position position ;

	private ArrayList<Position> bombPosition;
	
	private World world;
	
	private Semaphore atomicExplosion=new Semaphore(1);
	
	
	public Player(Color color , Position position) {
		this.color=color;
		this.numBomb=this.inkTank=TANK_VALUE;
		this.position=new Position(position);
		bombPosition=new ArrayList<>();
	}
	
	public Player(Color color , Position position , int inkTank) {
		this.color=color;
		this.numBomb=this.inkTank=inkTank;
		this.position=new Position(position);
		bombPosition=new ArrayList<>();
	}
	
	public Player(Player p){
		this.color=p.color;
		this.numBomb=this.inkTank=p.inkTank;
		this.position=new Position(p.position);
		bombPosition=new ArrayList<>();
		for(int i=0;i<p.bombPosition.size();i++){
			bombPosition.add(new Position(p.bombPosition.get(i)));
		}
	}
	
	public boolean placeBomb(Position p) {
		try {
			atomicExplosion.acquire();
		} catch (InterruptedException e) {}
		if(inkTank>0) {
			boolean okInsert=true;
			for(int i=0;i<bombPosition.size();i++){
				if(bombPosition.get(i).equals(p))okInsert=false;
			}
			if(okInsert){
				inkTank--;
				bombPosition.add(p);
				new Thread(new Bomb(p)).start();
			}
			atomicExplosion.release();
			return okInsert;
		}
		atomicExplosion.release();
		return false;
	}
	
	
	
	public void reloadTank() {
		if(inkTank==0)inkTank=numBomb;
	}
	
	public void reloadTankIfNotFull() {
		if(inkTank==numBomb) return;
		inkTank++;
	}
	
	public int  getX() {
		return position.getX();
	}
	
	public int getY() {
		return position.getY();
	}
	
	
	public final boolean equals(Object arg0) {
		return color.equals(arg0);
	}

	public final int hashCode() {
		return color.hashCode();
	}

	public boolean checkTank() {
		return inkTank>0;
	}
	
	public int getInkTank() {
		return inkTank;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void moveUp() {
		if(world==null) throw new UnassignedWorldException();
		if(position.getY()-1>=0) position.setY(position.getY()-1);
	}

	public void moveDown() {
		if(world==null) throw new UnassignedWorldException();
		if(position.getY()+1<world.getDimension()) position.setY(position.getY()+1);
	}

	public void moveLeft() {
		if(world==null) throw new UnassignedWorldException();
		if(position.getX()-1>=0) position.setX(position.getX()-1);
	}

	public void moveRight() {
		if(world==null) throw new UnassignedWorldException();
		if(position.getX()+1<world.getDimension()) position.setX(position.getX()-1);
	}
	
		
	
	public void setWorld(World world){
		this.world=world;
	}
	
	private class Bomb implements Runnable{
        
		private static final int TIME_EXPLOSION=3;
		
		private Position position;
		
		public Bomb(Position position) {
			this.position=position;
		}
		
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(TIME_EXPLOSION);
				Player.this.atomicExplosion.acquire();
				bombPosition.remove(position);
				Player.this.atomicExplosion.release();
				Player.this.world.paint(position, Player.this.color);
				World w=Player.this.world;
				w.startVictoryControl();
			    if(w.checkVictory(Player.this)) world.setWinningPlayer(Player.this);
			    else w.endVictoryControl();
			}catch(InterruptedException e) {}
		}
		
	}

}
