package logic;

public class UnassignedWorldException extends RuntimeException{
	
	public UnassignedWorldException() {
		super("Unassigned world for Player");
	}

}
