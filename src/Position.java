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
	
	@Override
	public String toString()
	{
		return "[ x:" + this.x + ", y:" + this.y + ", orientation:" + this.orientation + " ]";
	}
}
