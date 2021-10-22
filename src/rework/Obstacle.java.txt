package src.rework;

public class Obstacle extends SimObject
{
	public Obstacle()
	{
		super();
	}
	
	public Obstacle(double x, double y, int width, int height, double direction)
	{
		super(x, y, width, height, direction);
	}
	
	@Override
	String imageFileName()
	{
		return "obstacle.png";
	}

}
