package logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Enemy implements Runnable {

	private Player player;

	private ArrayList<Player> target;

	private Random random = new Random();

	private boolean targetFocussed;
	
	private World world;

	private int indexTarget, posxTarget, posyTarget;

	private static final int SHIFT = 4;

	private static final int THINK_MIN = 5;

	private static final int THINK_MAX = 7;

	private static final int MOV_MIN = 2;

	private static final int MOV_MAX = 4;

	private static final int CASES = 8;

	private State status = State.IDLE;

	private enum State {
		IDLE, LEFT, RIGHT, UP, DOWN
	}

	public Enemy(Player player, ArrayList<Player> target) {
		this.player = player;
		this.target = target;
		world=player.getWorld();
	}

	public void run() {
		ArrayList<Integer> tempTarget = new ArrayList<Integer>();
		Position tempPos=null;
		boolean firstCheck=true;
		boolean reloaded=false;
		while (true) {
			if (player.getInkTank()==0){
				for(int i=0;i<world.getDimension();i++){
					for(int j=0;j<world.getDimension();j++){
						if(world.getColorMatrix()[i][j]==player.getColor()&&firstCheck){
							tempPos=new Position(j,i);
						}
						else if (world.getColorMatrix()[i][j]==player.getColor()){
							int xf = player.getX() - j;
							int yf = player.getY() - i;
							double shift=Math.sqrt(Math.abs(xf) ^ 2 + Math.abs(yf) ^ 2);
							xf = player.getX() - tempPos.getX();
						    yf = player.getY() - tempPos.getY();
						    if(shift-Math.sqrt(Math.abs(xf) ^ 2 + Math.abs(yf) ^ 2)<0) tempPos=new Position(j,i);
						}
					}
					
				}
			}
			else if(!reloaded&&player.getInkTank()==0){
				if (player.getX() - tempPos.getX() > 0){
					player.moveLeft();
					reloaded=player.getInkTank()>0;
				}
				else if (player.getX() - tempPos.getX() < 0){
					player.moveRight();
					reloaded=player.getInkTank()>0;
				}
				else if (player.getY() - tempPos.getY() > 0){
					player.moveUp();
					reloaded=player.getInkTank()>0;
				}
				else if (player.getY() - tempPos.getY() < 0){
					player.moveDown();
					reloaded=player.getInkTank()>0;
				}
			}
			else if (!targetFocussed) {
				for (int i = 0; i < target.size(); i++) {
					if (checkShift(player, target.get(i)))
						tempTarget.add(i);
				}
			}
			if (tempTarget.size() > 0) {
				if (!targetFocussed) {
					indexTarget = random.nextInt(tempTarget.size());
					posxTarget = target.get(tempTarget.get(indexTarget)).getX();
					posyTarget = target.get(tempTarget.get(indexTarget)).getY();
					targetFocussed = true;
				} else {
					if (player.getX() - posxTarget > 0)
						player.moveLeft();
					else if (player.getX() - posxTarget < 0)
						player.moveRight();
					else if (player.getY() - posyTarget > 0)
						player.moveUp();
					else if (player.getY() - posyTarget < 0)
						player.moveDown();
					else {
						player.placeBomb(player.getPosition());
						targetFocussed = false;
						tempTarget.clear();
					}

				}
				try {
					TimeUnit.SECONDS.sleep(random.nextInt(MOV_MAX - MOV_MIN + 1) + MOV_MIN);
				} catch (InterruptedException e) {
				}
			} else {
				
				/*boolean okAction = setAction(random.nextInt(CASES));
				while (!okAction)
					okAction = setAction(random.nextInt(CASES));
				try {
					TimeUnit.SECONDS.sleep(random.nextInt(THINK_MAX - THINK_MIN + 1) + THINK_MIN);
				} catch (InterruptedException e) {
				}*/
			}
		}
	}

	private boolean setAction(int action) {
		if (action == 1 && status != State.DOWN) {
			player.moveUp();
			status = State.UP;
			return true;
		}
		if (action == 2 && status != State.UP) {
			player.moveDown();
			status = State.DOWN;
			return true;
		}
		if (action == 3 && status != State.RIGHT) {
			player.moveLeft();
			status = State.LEFT;
			return true;
		}
		if (action == 4 && status != State.LEFT) {
			player.moveRight();
			status = State.RIGHT;
			return true;
		}
		if ((action == 5 || action == 6 || action == 7 || action == 8)) {
			player.placeBomb(player.getPosition());
			status = State.IDLE;
			return true;
		}
		return false;

	}
	
	private int checkMatrix(Color[][] world,int x , int y){
		int cont =0;
		if(world[y][x]==player.getColor()) cont++;
		if(y-1>=0&&world[y-1][x]==player.getColor()) cont++;
		if(y+1<world.length&&world[y+1][x]==player.getColor()) cont++;
		if(y-1>=0&&x-1>=0&&world[y-1][x-1]==player.getColor()) cont++;
		if(y-1>=0&&x+1<world.length&&world[y-1][x+1]==player.getColor()) cont++;
		if(x-1>=0&&world[y][x-1]==player.getColor()) cont++;
		if(x+1<world.length&&world[y][x+1]==player.getColor()) cont++;
		if(y+1<world.length&&x-1>=0&&world[y-1][x-1]==player.getColor()) cont++;
		if(y+1<world.length&&x+1<world.length&&world[y-1][x+1]==player.getColor()) cont++;
		return cont;
	}

	private static boolean checkShift(Player p1, Player p2) {
		int xf = p1.getX() - p2.getX();
		int yf = p1.getY() - p2.getY();
		return Math.sqrt(Math.abs(xf) ^ 2 + Math.abs(yf) ^ 2) < SHIFT;
	}

}
