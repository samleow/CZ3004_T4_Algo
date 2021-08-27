package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.Position.Orientation;

public class HamiltonianPath
{
	public HamiltonianPath()
	{
		//List<Position> obstacles = new ArrayList<Position>();
		//obstacles.add(0, new Position(1, 1, Orientation.NORTH));
		
		// List of waypoints/nodes
		List<CarPosition> positions = new ArrayList<CarPosition>();
		positions.add(new CarPosition(1, 1, Orientation.WEST));
		positions.add(new CarPosition(8, 5, Orientation.NORTH));
		positions.add(new CarPosition(7, 3, Orientation.EAST));
		positions.add(new CarPosition(3, 3, Orientation.WEST));
		
		List<CarPosition> shortest_path = new ArrayList<CarPosition>();
		CarPosition start = new CarPosition(1, 4.5, Orientation.SOUTH);
		shortest_path.add(start);
		double total_dist = 0;
		CarPosition temp = start;
		
		// Loops until shortest_path is complete
		for (int i=0; i<positions.size(); i++)
		{
			// Initialized to maximum possible distance in the grid
			double traveldist = Math.sqrt(2 * Math.pow(20, 2));
			// Loops through all waypoints for shortest neighbour link
			for (CarPosition ac : positions)
			{
				double pythagoras = Math.sqrt(Math.pow(start.x - ac.x, 2) + Math.pow(start.y - ac.y, 2));
				if (pythagoras < traveldist && ac.visited != true)
				{
					traveldist = pythagoras;
					temp = ac;
				}
			}
			total_dist += traveldist;
			temp.setvisited(true);
			start = temp;
			shortest_path.add(temp);
		}
		
		System.out.println("Hamiltonian Path:");
		System.out.println(shortest_path);
		System.out.println(total_dist);
		
		List<ExhaustiveSearch> double_check = new ArrayList<ExhaustiveSearch>();
		List<CarPosition> path = new ArrayList<CarPosition>();
		ExhaustiveSearch item;
		double_check.add(new ExhaustiveSearch(1.5, 1.5, 0, positions, path));
		List<CarPosition> fastest_path = new ArrayList<CarPosition>();
		while (double_check.size() != 0)
		{
			item = double_check.get(0);
			
			if (item.neighbours.size() == 0)
			{
				break;
			}
			else
			{
				double_check.remove(0);
				for (int i = 0; i < item.neighbours.size(); i++)
				{
					double new_x = item.getNeighbours().get(i).x;
					double new_y = item.getNeighbours().get(i).y;
					double new_cost = item.getCost()
							+ Math.sqrt(Math.pow(item.x - new_x, 2) + Math.pow(item.y - new_y, 2));
					List<CarPosition> new_path = new ArrayList<CarPosition>(item.getPath());
					new_path.add(item.getNeighbours().get(i));
					List<CarPosition> new_neighbours = new ArrayList<CarPosition>(item.getNeighbours());
					new_neighbours.remove(i);
					if (new_neighbours.size() == 0 && new_cost < total_dist)
					{
						fastest_path = new_path;
						total_dist = new_cost;
					}
					double_check.add(new ExhaustiveSearch(new_x, new_y, new_cost, new_neighbours, new_path));
				}
			}
		}
		
		System.out.println("\nExhaustive Search:");
		System.out.println(fastest_path);
		System.out.println(total_dist);
		
		List<CarPosition> order_swapped_path = orderSwapCheck(shortest_path);
		System.out.println("\nOrder Swap Check to optimize Hamiltonian Path:");
		System.out.println(order_swapped_path);
		System.out.println(calculateCost(order_swapped_path));
		
	}
	
	// Improvement of Hamiltonian Path
	List<CarPosition> orderSwapCheck(List<CarPosition> path)
	{
		if(path == null || path.size() == 0)
			return null;
		if(path.size() < 3)
			return path;
		
		List<CarPosition> new_path = new ArrayList<CarPosition>(path);
		double new_cost = calculateCost(new_path);
		
		List<CarPosition> temp_path = new ArrayList<CarPosition>(path);
		for(int i=1; i<path.size()-2; i++)
		{
			Collections.swap(temp_path, i, i+1);
			double temp_cost = calculateCost(temp_path);
			if(temp_cost < new_cost)
			{
				Collections.copy(new_path, temp_path);
				new_cost = temp_cost;
			}
			else
				Collections.copy(temp_path, new_path);
		}
		
		return new_path;
	}
	
	// Calculates cost of a path
	double calculateCost(List<CarPosition> path)
	{
		if(path == null || path.size() < 2)
			return 0.0;
		
		double cost = 0.0;
		for(int i=0; i<path.size()-1; i++)
		{
			cost += Math.sqrt(Math.pow(path.get(i).x - path.get(i+1).x, 2) + Math.pow(path.get(i).y - path.get(i+1).y, 2));
		}
		
		return cost;
	}
	
	public static void main(String args[])
	{
		HamiltonianPath a = new HamiltonianPath();

	}
	
}
