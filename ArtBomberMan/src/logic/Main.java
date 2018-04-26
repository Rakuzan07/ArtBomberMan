package logic;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
	
	public static void main(String...args) {
		Player playerOne=new Player(Color.GREEN , new Position(25,49));
		ArrayList<Player> players=new ArrayList<>();
		players.add(new Player(Color.BLUE , new Position(0,0)));
		players.add(new Player(Color.PURPLE , new Position(0,49)));
		World world=new World(playerOne , players);
		playerOne.setWorld(world);
		players.get(0).setWorld(world);
		players.get(1).setWorld(world);
		System.out.println(world);
		world.generateEnemy();
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(20);
				System.out.println(world);
			}catch(InterruptedException e) {}
		}
	}

}
