package src;

import java.lang.Math;

public class CarPosition extends Position
{
	double car_facing;
	boolean visited;

	public CarPosition(double x, double y, Orientation orientation)
	{
		super(x, y, orientation);
		if (orientation == Orientation.SOUTH)
		{
			this.x = x + 0.5;
			this.y = y - 3;
			car_facing = (Math.PI) / 2;
			visited = false;
		}
		else if (orientation == Orientation.NORTH)
		{
			this.x = x + 0.5;
			this.y = y + 4;
			car_facing = -(Math.PI) / 2;
			visited = false;
		}
		else if (orientation == Orientation.EAST)
		{
			this.x = x + 4;
			this.y = y + 0.5;
			car_facing = Math.PI;
			visited = false;
		}
		else if (orientation == Orientation.WEST)
		{
			this.x = x - 3;
			this.y = y + 0.5;
			car_facing = 0.0;
			visited = false;
		}
		else
		{
			System.out.println("Orientation was not found");
		}
		
	}
	
	public boolean getvisited()
	{
		return visited;
	}
	
	public void setvisited(boolean visited)
	{
		this.visited = visited;
	}
	
}
