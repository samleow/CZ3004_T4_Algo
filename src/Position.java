package src;

public class Position
{
	public enum Orientation
	{
		NORTH, SOUTH, EAST, WEST
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
	
	@Override
	public String toString()
	{
		return "[ x:" + this.x + ", y:" + this.y + ", orientation:" + this.orientation + " ]";
	}
}
