package logic;

import java.util.LinkedList;

public class Player {
	
	private static final int TANK_VALUE=4;
	
	private int inkTank , numBomb;
	
	private Color color;
	
	private Position position ;

	private LinkedList<Position> bombPosition;
	
	public Player(Color color , Position position) {
		this.color=color;
		this.numBomb=this.inkTank=TANK_VALUE;
		this.position=new Position(position);
		bombPosition=new LinkedList<>();
	}
	
	public Player(Color color , Position position , int inkTank) {
		this.color=color;
		this.numBomb=this.inkTank=inkTank;
		this.position=new Position(position);
		bombPosition=new LinkedList<>();
	}
	
	public Player(Player p){
		this.color=p.color;
		this.numBomb=this.inkTank=p.inkTank;
		this.position=new Position(p.position);
		bombPosition=new LinkedList<>();
		for(int i=0;i<p.bombPosition.size();i++){
			bombPosition.addLast(new Position(p.bombPosition.get(i)));
		}
	}
	
	public boolean placeBomb(Position p) {
		if(inkTank>0) {
			boolean okInsert=true;
			for(int i=0;i<bombPosition.size();i++){
				if(bombPosition.get(i).equals(p))okInsert=false;
			}
			if(okInsert){
				inkTank--;
				bombPosition.addLast(p);
			}
			return okInsert;
		}
		return false;
	}
	
	public void reloadTank() {
		inkTank=numBomb;
	}
	
	public void reloadTankIfNotFull() {
		if(inkTank==numBomb) return;
		inkTank++;
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

}
