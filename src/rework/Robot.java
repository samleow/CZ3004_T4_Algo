package src.rework;

import java.util.List;

import src.CarPosition;

public class Robot extends SimObject
{
	// Might need to store list of commands here

	private static Robot _instance = null;
	
	List<CarPosition> waypoints = null;
	int current_wp = 0;
	
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

	@Override
	String imageFileName()
	{
		return "robot.png";
	}
	
	public void planRoute()
	{
		// TODO: A* path finding here
		// for waypoints.get(current_waypoint)
		// when reached waypoint, current_wp++
		
		
	}
	
	public void update()
	{
		setDirection(direction - 0.01);
		
		// movement update here
		// must be based on delta time
		x += 1.5 * Math.cos(direction);
		y += 1.5 * Math.sin(direction);
		
		// when reached waypoint, current_wp++
	}
	
}
