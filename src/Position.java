package src;

public class Position
{
	public enum Orientation
	{
		NORTH, EAST, WEST, SOUTH
	};
	
	double x;
	double y;
	Orientation orientation;
	
	public Position(double x, double y, Orientation orientation)
	{
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public Orientation getOrientation()
	{
		return orientation;
	}
	
	public double getDirection()
	{
		switch(orientation)
		{
			case NORTH:
				return Math.PI/2;
			case EAST:
				return 0.0;
			case WEST:
				return Math.PI;
			case SOUTH:
				return -Math.PI/2;
			default:
				return -1.0;
		}
	}
	
	@Override
	public String toString()
	{
		return "[ x:" + this.x + ", y:" + this.y + ", orientation:" + this.orientation + " ]";
	}
}
