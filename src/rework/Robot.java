package src.rework;

import java.util.ArrayList;
import java.util.List;

import src.CarPosition;
import src.Position;
import src.AStar.AStar;
import src.AStar.Node;
import src.rework.Command.CommandType;

public class Robot extends SimObject
{
	// Might need to store list of commands here

	private static Robot _instance = null;
	public int[][] barrier = new int[500][2];
	
	List<CarPosition> waypoints = null;
	public List<Position> obstacles = null;
	List<Command> commands = new ArrayList<Command>();
	List<Node> path = null;
	
	int current_wp = 0;
	int current_command = 0;
	int current_path = 0;
	
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
		
		// create commands
		int counter = 0;
		for (int i=0;i<obstacles.size();i++)
		{
			for (int j=0; j<3;j++)
			{
				for (int k= 0; k<3; k++)
				{
					if ((int)obstacles.get(i).getX()+j< 20 && (int)obstacles.get(i).getX()-j> 0 && (int)obstacles.get(i).getY()+k< 20 && (int)obstacles.get(i).getY()-k> 0)
					{
						barrier[counter][0] = (int)obstacles.get(i).getX() + j;
						barrier[counter][1] = (int)obstacles.get(i).getY() + k;
						counter++;
						barrier[counter][0] = (int)obstacles.get(i).getX() - j;
						barrier[counter][1] = (int)obstacles.get(i).getY() - k;
						counter++;
						barrier[counter][0] = (int)obstacles.get(i).getX() + j;
						barrier[counter][1] = (int)obstacles.get(i).getY() - k;
						counter++;
						barrier[counter][0] = (int)obstacles.get(i).getX() - j;
						barrier[counter][1] = (int)obstacles.get(i).getY() + k;
						counter++;
					}
				}
			}
		}
		
		path = new ArrayList<Node>();
		for (int i =0; i<waypoints.size()-1; i++)
		{
			AStar astar = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier);
			astar.display();
			astar.process();
			astar.displaySolution();
			path.add(astar.getSolutionNode());
		}
		
		generateCommands(path.get(current_path));
		
		/*
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.TURN, -Math.PI/2));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.TURN, Math.PI/2));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		*/
	}
	
	void generateCommands(Node node)
	{
		commands = null;
		commands = new ArrayList<Command>();
		double curr_dir = 0.0, prev_dir = 0.0;
		
		while(node.parent != null)
		{
			curr_dir = getNodeDirection(node, node.parent);
			// problem here
			// might need to iterate backwards
			// TODO:
			if(node.parent.parent != null)
				prev_dir = getNodeDirection(node.parent, node.parent.parent);
			else
				prev_dir = 0.0;
			
			generateTurnCommands(curr_dir, prev_dir);
			commands.add(0, new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
			node = node.parent;
		}
	}
	
	double getNodeDirection(Node curr, Node prev)
	{
		double dir = 0.0;
		// move west
		if(curr.i < prev.i)
		{
			dir = Math.PI;
		}
		// move east
		else if(curr.i > prev.i)
		{
			dir = 0.0;
		}
		// move north
		else if(curr.j > prev.j)
		{
			dir = Math.PI/2;
		}
		// move south
		else if(curr.j > prev.j)
		{
			dir = -Math.PI/2;
		}
		
		return dir;
	}
	
	void generateTurnCommands(double target_dir, double prev_dir)
	{
		if(target_dir == prev_dir)
			return;
		
		if(Math.abs(target_dir - prev_dir) == Math.PI)
		{
			commands.add(0, new Command(CommandType.TURN, Math.PI/2));
			commands.add(0, new Command(CommandType.TURN, Math.PI/2));
		}		
		else if(Math.abs(target_dir - prev_dir) < Math.PI)
		{
			if(target_dir-prev_dir < 0)
				commands.add(0, new Command(CommandType.TURN, -Math.PI/2));
			else
				commands.add(0, new Command(CommandType.TURN, Math.PI/2));
			
		}
	}
	
	public void update()
	{
		// testing steering
		/*
		setDirection(direction - 0.01);
		x += Math.cos(direction);
		y += Math.sin(direction);
		*/
		
		// movement update here
		// must be based on delta time
		if(commands == null || commands.size() == 0)
			return;
		
		Command currCmd = commands.get(current_command);
		
		switch(currCmd.command_type)
		{
			case WAIT:
				break;
			case MOVE:
				x += Math.cos(direction) * currCmd.arg1;
				y += Math.sin(direction) * currCmd.arg1;
				break;
			case TURN:
				setDirection(direction + currCmd.arg1);
				break;
			default:
				break;
		}
		
		// when reached waypoint, current_wp++
		if(current_command+1 >= commands.size())
		{
			SimulatorManager.getInstance().timer.stop();
		}
		else
		{
			current_command++;
		}
	}
	
}
