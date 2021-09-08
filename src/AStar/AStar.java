package src.AStar;
import src.CarPosition;
import src.HamiltonianPathSimulator;
import src.Position;
import src.readinput;

import java.util.ArrayList;
import java.util.List;

public class AStar
{
    public AStar()
    {
    	
    }
    
    public List<Node> generateGraph()
    {
    	
    	
    	return null;
    }
    
    public List<Node> generateGraph(List<CarPosition> path)
    {
    	List<Node> nl = new ArrayList<Node>();
    	
    	for(int i=0; i< path.size(); i++)
    	{
    		Node n = new Node();
    	}
    	
    	return null;
    }
    
    public static void main(String[] args)
    {
    	List<CarPosition> target = new HamiltonianPathSimulator(new readinput().getObstacles()).getCarPositions();
    	
    	
    	
    	Node n1 = new Node();
    	
    	
    }
}
