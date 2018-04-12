package logic;

import java.util.LinkedList;

public class Player {
	
	private static final int TANK_VALUE=4;
	
	private int inkTank , numBomb;
	
	private Color color;
	
	private Position position ;

	private LinkedList<Position> bombPosition;
	
	private Status state;
	
	private boolean worldPermit;
	
	public enum Status{IDLE,UP,DOWN,RIGHT,LEFT}
	
	public Player(Color color , Position position) {
		this.color=color;
		this.numBomb=this.inkTank=TANK_VALUE;
		this.position=new Position(position);
		bombPosition=new LinkedList<>();
		state=Status.IDLE;
		worldPermit=false;
	}
	
	public Player(Color color , Position position , int inkTank) {
		this.color=color;
		this.numBomb=this.inkTank=inkTank;
		this.position=new Position(position);
		bombPosition=new LinkedList<>();
		state=Status.IDLE;
		worldPermit=false;
	}
	
	public Player(Player p){
		this.color=p.color;
		this.numBomb=this.inkTank=p.inkTank;
		this.position=new Position(p.position);
		bombPosition=new LinkedList<>();
		for(int i=0;i<p.bombPosition.size();i++){
			bombPosition.addLast(new Position(p.bombPosition.get(i)));
		}
		state=Status.IDLE;
		worldPermit=false;
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
	
	public Status getState() {
		return state;
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
		state=Status.UP;
	}

	public void moveDown() {
		state=Status.DOWN;
	}

	public void moveLeft() {
		state=Status.LEFT;
	}

	public void moveRight() {
		state=Status.RIGHT;
	}
	
	public void move() {
		if(!worldPermit) throw new ControlException();
		worldPermit=false;
		if (state==Status.UP) position.setY(position.getY()-1);
		else if(state==Status.DOWN) position.setX(position.getY()+1);
		else if(state==Status.RIGHT) position.setX(position.getX()+1);
		else if(state==Status.LEFT) position.setX(position.getX()-1);
		state=Status.IDLE;
	}
	
	public void permit() {
		worldPermit=true;
	}

}
