package src.rework;

import java.util.ArrayList;
import java.util.List;

import src.CarPosition;
import src.AStar.AStar;
import src.rework.Command.CommandType;

public class Robot extends SimObject
{
	// Might need to store list of commands here

	private static Robot _instance = null;
	public int[][] barrier = new int[500][2];
	
	List<CarPosition> waypoints = null;
	List<Command> commands = new ArrayList<Command>();
	
	int current_wp = 0;
	int current_command = 0;
	
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
					if ((int)obstacles.get(i).x+j< 20 && (int)obstacles.get(i).x-j> 0 && (int)obstacles.get(i).y+k< 20 && (int)obstacles.get(i).y-k> 0)
					{
						barrier[counter][0] = (int)obstacles.get(i).x + j;
						barrier[counter][1] = (int)obstacles.get(i).y + k;
						counter++;
						barrier[counter][0] = (int)obstacles.get(i).x - j;
						barrier[counter][1] = (int)obstacles.get(i).y - k;
						counter++;
						barrier[counter][0] = (int)obstacles.get(i).x + j;
						barrier[counter][1] = (int)obstacles.get(i).y - k;
						counter++;
						barrier[counter][0] = (int)obstacles.get(i).x - j;
						barrier[counter][1] = (int)obstacles.get(i).y + k;
						counter++;
					}
				}
			}
		}
		
		for (int i =0; i<positions.size()-1; i++)
		{
			AStar astar = new AStar(20, 20, (int)positions.get(i).x, (int)positions.get(i).y, (int)positions.get(i+1).x, (int)positions.get(i+1).y, barrier);
			astar.display();
			astar.process();
			astar.displaySolution();
		}
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.TURN, -Math.PI/2));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.TURN, Math.PI/2));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
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
