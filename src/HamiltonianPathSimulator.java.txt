package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.Position.Orientation;

public class HamiltonianPathSimulator
{
	List<CarPosition> fastest_path = new ArrayList<CarPosition>();
	Double TurningRadius = 20.0;
	public HamiltonianPathSimulator(List<Position> obstacles)
	{
		
		List<CarPosition> positions = new ArrayList<CarPosition>();
		// List of waypoints/nodes
		for (Position obs: obstacles)
		{
			positions.add(new CarPosition(obs.x, obs.y, obs.orientation, true));
		}
		
		List<CarPosition> shortest_path = new ArrayList<CarPosition>();
		CarPosition start = new CarPosition(1, 5, Orientation.SOUTH, true); //1, 4.5, Orientation.SOUTH, true); //calibrate to 1,1
		shortest_path.add(start);
		double total_dist = 0;
		CarPosition temp = start;
		
		// Loops until shortest_path is complete
		for (int i=0; i<positions.size(); i++)
		{
			// Initialized to maximum possible distance in the grid
			double traveldist = Math.sqrt(2 * Math.pow(20, 2)) + 4*20.0; //added the turning radius
			// Loops through all waypoints for shortest neighbour link
			for (CarPosition ac : positions)
			{
				double pythagoras = Math.sqrt(Math.pow(start.x - ac.x, 2) + Math.pow(start.y - ac.y, 2));
				/*switch(start.orientation) {
						//NORTH
						case NORTH:
						  if (ac.orientation==Orientation.WEST && ac.x<start.x) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.EAST && ac.x>start.x) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.NORTH) {
							pythagoras += 2*TurningRadius;
							break;
						  }
						//EAST
						case EAST:
						  if (ac.orientation==Orientation.NORTH && ac.y>start.y) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.SOUTH && ac.y<start.y) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.EAST) {
							pythagoras += 2*TurningRadius;
							break;
						  }
						//SOUTH
						case SOUTH:
						  if (ac.orientation==Orientation.WEST && ac.x<start.x) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.EAST && ac.x>start.x) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.SOUTH) {
							pythagoras += 2*TurningRadius;
							break;
						  }
						//WEST
						case WEST:
						  if (ac.orientation==Orientation.NORTH && ac.x>start.y) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.SOUTH && ac.x<start.y) {
							pythagoras += 3*TurningRadius;
							break;
						  }
						  else if (ac.orientation==Orientation.WEST) {
							pythagoras += 2*TurningRadius;
							break;
						  }
					  }*/
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
		
		//Exhaustive search
		List<ExhaustiveSearch> double_check = new ArrayList<ExhaustiveSearch>();
		List<CarPosition> path = new ArrayList<CarPosition>();
		ExhaustiveSearch item;
		path.add(new CarPosition(1, 5, Orientation.SOUTH, true)); //1, 4.5, Orientation.SOUTH, true)); //add start point to path
		double_check.add(new ExhaustiveSearch(1.5, 1.5, 0, positions, path)); //initialise start point for exhaustive search
		
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
				for (int i = 0; i < item.getNeighbours().size(); i++)
				{
					double new_x = item.getNeighbours().get(i).x;
					double new_y = item.getNeighbours().get(i).y;
					double dist = Math.sqrt(Math.pow(item.x - new_x, 2) + Math.pow(item.y - new_y, 2));
					int size = item.path.size();
					/*switch(item.path.get(size-1).orientation) {
						//NORTH
						case NORTH:
						  if (item.getNeighbours().get(i).orientation==Orientation.WEST && new_x<item.path.get(size-1).getX()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).orientation==Orientation.EAST && new_x>item.path.get(size-1).getX()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).orientation==Orientation.NORTH) {
							dist += 2*TurningRadius;
							break;
						  }
						//EAST
						case EAST:
						  if (item.getNeighbours().get(i).getOrientation()==Orientation.NORTH && new_y>item.path.get(size-1).getY()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).getOrientation()==Orientation.SOUTH && new_y<item.path.get(size-1).getY()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).getOrientation()==Orientation.EAST) {
							dist += 2*TurningRadius;
							break;
						  }
						//SOUTH
						case SOUTH:
						  if (item.getNeighbours().get(i).getOrientation()==Orientation.WEST && new_x<item.path.get(size-1).getX()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).getOrientation()==Orientation.EAST && new_x>item.path.get(size-1).getX()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).getOrientation()==Orientation.SOUTH) {
							dist += 2*TurningRadius;
							break;
						  }
						//WEST
						case WEST:
						  if (item.getNeighbours().get(i).getOrientation()==Orientation.NORTH && new_y>item.path.get(size-1).getY()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).getOrientation()==Orientation.SOUTH && new_y<item.path.get(size-1).getY()) {
							dist += 3*TurningRadius;
							break;
						  }
						  else if (item.getNeighbours().get(i).getOrientation()==Orientation.WEST) {
							dist += 2*TurningRadius;
							break;
						  }
					  }*/
					double new_cost = item.getCost()
							+ dist;
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
	public List<CarPosition> getCarPositions(){
		return fastest_path;
	}
	
}
