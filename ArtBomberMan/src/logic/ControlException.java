package logic;

public class ControlException extends RuntimeException{
	

	public ControlException() {
		super("The player must request permission from the world");
	}

}
