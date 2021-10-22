package src;

import java.util.List;

public class ExhaustiveSearch
{
	double x;
	double y;
	double cost;
	List<CarPosition> neighbours;
	List<CarPosition> path;
	
	public ExhaustiveSearch(double x, double y, double cost, List<CarPosition> neighbours, List<CarPosition> path)
	{
		this.x = x;
		this.y = y;
		this.cost = cost;
		this.neighbours = neighbours;
		this.path = path;
	}
	
	public List<CarPosition> getNeighbours()
	{
		return neighbours;
	}
	
	public List<CarPosition> getPath()
	{
		return path;
	}
	
	public double getCost()
	{
		return cost;
	}
	
}
