package src.rework;

public class Robot extends SimObject
{
	// Might need to store list of commands here

	private static Robot _instance = null;
	
	private Robot()
	{
		super();
	}
	
	public static Robot getInstance()
	{
		if(_instance == null)
		{
			_instance = new Robot();
		}
		
		return _instance;
	}
	
	public void update()
	{
		direction += 0.01;
		
		// movement update here
		// must be based on delta time
		x += 1.5 * Math.cos(direction);
		y += 1.5 * Math.sin(direction);
	}

	@Override
	String imageFileName()
	{
		return "robot.png";
	}
	
}
