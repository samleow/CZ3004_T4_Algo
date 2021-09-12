package src.rework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.CarPosition;
import src.Position;
import src.AStar.AStar;
import src.AStar.Node;
import src.Position.Orientation;
import src.rework.Command.CommandType;

public class Robot extends SimObject
{
	// Might need to store list of commands here

	private static Robot _instance = null;
	public int[][] barrier = new int[500][2];
	private static final int North=1, East= 2, South = 3, West =4;
	List<CarPosition> waypoints = null;
	public List<Position> obstacles = null;
	List<Command> commands = new ArrayList<Command>();
	List<Node> path = null;
	int ori = North;
	String plannedmovement = "";
	ArrayList<String> steps;
	int dir = ori;
	int stage =0;
	
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
			if ((int)obstacles.get(i).getX()< 20 && (int)obstacles.get(i).getX()>= 0 && (int)obstacles.get(i).getY()< 20 && (int)obstacles.get(i).getY()>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX();
				barrier[counter][1] = (int)obstacles.get(i).getY();
				counter++;
			}
			if ((int)obstacles.get(i).getX()+1< 20 && (int)obstacles.get(i).getX()+1>= 0 && (int)obstacles.get(i).getY()+1< 20 && (int)obstacles.get(i).getY()+1>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX() + 1;
				barrier[counter][1] = (int)obstacles.get(i).getY() + 1;
				counter++;
			}
			if ((int)obstacles.get(i).getX()-1< 20 && (int)obstacles.get(i).getX()-1>= 0 && (int)obstacles.get(i).getY()-1< 20 && (int)obstacles.get(i).getY()-1>= 0)	
			{
				barrier[counter][0] = (int)obstacles.get(i).getX() - 1;
				barrier[counter][1] = (int)obstacles.get(i).getY() - 1;
				counter++;
			}	
			if ((int)obstacles.get(i).getX()+1< 20 && (int)obstacles.get(i).getX()+1>= 0 && (int)obstacles.get(i).getY()-1< 20 && (int)obstacles.get(i).getY()-1>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX() + 1;
				barrier[counter][1] = (int)obstacles.get(i).getY() - 1;
				counter++;
			}
			if ((int)obstacles.get(i).getX()-1< 20 && (int)obstacles.get(i).getX()-1>= 0 && (int)obstacles.get(i).getY()+1< 20 && (int)obstacles.get(i).getY()+1>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX() - 1;
				barrier[counter][1] = (int)obstacles.get(i).getY() + 1;
				counter++;
			}	
			if ((int)obstacles.get(i).getX()+1< 20 && (int)obstacles.get(i).getX()+1>= 0 && (int)obstacles.get(i).getY()< 20 && (int)obstacles.get(i).getY()>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX() + 1;
				barrier[counter][1] = (int)obstacles.get(i).getY();
				counter++;
			}	
			if ((int)obstacles.get(i).getX()< 20 && (int)obstacles.get(i).getX()>= 0 && (int)obstacles.get(i).getY()+1< 20 && (int)obstacles.get(i).getY()+1>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX();
				barrier[counter][1] = (int)obstacles.get(i).getY() + 1;
				counter++;
			}	
			if ((int)obstacles.get(i).getX()-1< 20 && (int)obstacles.get(i).getX()-1>= 0 && (int)obstacles.get(i).getY()< 20 && (int)obstacles.get(i).getY()>= 0)
			{
				barrier[counter][0] = (int)obstacles.get(i).getX() - 1;
				barrier[counter][1] = (int)obstacles.get(i).getY();
				counter++;
			}	
			if ((int)obstacles.get(i).getX()< 20 && (int)obstacles.get(i).getX()>= 0 && (int)obstacles.get(i).getY()-1< 20 && (int)obstacles.get(i).getY()-1>= 0)
			{	
				barrier[counter][0] = (int)obstacles.get(i).getX();
				barrier[counter][1] = (int)obstacles.get(i).getY() - 1;
				counter++;
			}
			if (obstacles.get(i).getOrientation() == Orientation.NORTH)
			{
				if ((int)obstacles.get(i).getX()+1< 20 && (int)obstacles.get(i).getX()+1>= 0 && (int)obstacles.get(i).getY()+2< 20 && (int)obstacles.get(i).getY()+2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+1;
					barrier[counter][1] = (int)obstacles.get(i).getY()+2;
					counter++;
				}
				if ((int)obstacles.get(i).getX()-1< 20 && (int)obstacles.get(i).getX()-1>= 0 && (int)obstacles.get(i).getY()+2< 20 && (int)obstacles.get(i).getY()+2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-1;
					barrier[counter][1] = (int)obstacles.get(i).getY()+2;
					counter++;
				}
				/*if ((int)obstacles.get(i).getX()+3< 20 && (int)obstacles.get(i).getX()+3>= 0 && (int)obstacles.get(i).getY()+2< 20 && (int)obstacles.get(i).getY()+2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+3;
					barrier[counter][1] = (int)obstacles.get(i).getY()+2;
					counter++;
				}
				if ((int)obstacles.get(i).getX()-3< 20 && (int)obstacles.get(i).getX()-3>= 0 && (int)obstacles.get(i).getY()+2< 20 && (int)obstacles.get(i).getY()+2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-3;
					barrier[counter][1] = (int)obstacles.get(i).getY()+2;
					counter++;
				}*/
			}
			else if (obstacles.get(i).getOrientation() == Orientation.SOUTH)
			{
				if ((int)obstacles.get(i).getX()+1< 20 && (int)obstacles.get(i).getX()+1>= 0 && (int)obstacles.get(i).getY()-2< 20 && (int)obstacles.get(i).getY()-2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+1;
					barrier[counter][1] = (int)obstacles.get(i).getY()-2;
					counter++;
				}
				if ((int)obstacles.get(i).getX()-1< 20 && (int)obstacles.get(i).getX()-1>= 0 && (int)obstacles.get(i).getY()-2< 20 && (int)obstacles.get(i).getY()-2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-1;
					barrier[counter][1] = (int)obstacles.get(i).getY()-2;
					counter++;
				}
				/*if ((int)obstacles.get(i).getX()+3< 20 && (int)obstacles.get(i).getX()+3>= 0 && (int)obstacles.get(i).getY()-2< 20 && (int)obstacles.get(i).getY()-2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+3;
					barrier[counter][1] = (int)obstacles.get(i).getY()-2;
					counter++;
				}
				if ((int)obstacles.get(i).getX()-3< 20 && (int)obstacles.get(i).getX()-3>= 0 && (int)obstacles.get(i).getY()-2< 20 && (int)obstacles.get(i).getY()-2>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-3;
					barrier[counter][1] = (int)obstacles.get(i).getY()-2;
					counter++;
				}*/
			}
			else if (obstacles.get(i).getOrientation() == Orientation.EAST)
			{
				if ((int)obstacles.get(i).getX()+2< 20 && (int)obstacles.get(i).getX()+2>= 0 && (int)obstacles.get(i).getY()-1< 20 && (int)obstacles.get(i).getY()-1>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+2;
					barrier[counter][1] = (int)obstacles.get(i).getY()-1;
					counter++;
				}
				if ((int)obstacles.get(i).getX()+2< 20 && (int)obstacles.get(i).getX()+2>= 0 && (int)obstacles.get(i).getY()+1< 20 && (int)obstacles.get(i).getY()+1>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+2;
					barrier[counter][1] = (int)obstacles.get(i).getY()+1;
					counter++;
				}	
				/*if ((int)obstacles.get(i).getX()+2< 20 && (int)obstacles.get(i).getX()+2>= 0 && (int)obstacles.get(i).getY()+3< 20 && (int)obstacles.get(i).getY()+3>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+2;
					barrier[counter][1] = (int)obstacles.get(i).getY()+3;
					counter++;
				}
				if ((int)obstacles.get(i).getX()+2< 20 && (int)obstacles.get(i).getX()+2>= 0 && (int)obstacles.get(i).getY()-3< 20 && (int)obstacles.get(i).getY()-3>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()+2;
					barrier[counter][1] = (int)obstacles.get(i).getY()-3;
					counter++;
				}*/
			}	
			else if (obstacles.get(i).getOrientation() == Orientation.WEST)
			{
				if ((int)obstacles.get(i).getX()-2< 20 && (int)obstacles.get(i).getX()-2>= 0 && (int)obstacles.get(i).getY()-1< 20 && (int)obstacles.get(i).getY()-1>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-2;
					barrier[counter][1] = (int)obstacles.get(i).getY()-1;
					counter++;
				}
				if ((int)obstacles.get(i).getX()-2< 20 && (int)obstacles.get(i).getX()-2>= 0 && (int)obstacles.get(i).getY()+1< 20 && (int)obstacles.get(i).getY()+1>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-2;
					barrier[counter][1] = (int)obstacles.get(i).getY()+1;
					counter++;
				}
				/*if ((int)obstacles.get(i).getX()-2< 20 && (int)obstacles.get(i).getX()-2>= 0 && (int)obstacles.get(i).getY()+3< 20 && (int)obstacles.get(i).getY()+3>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-2;
					barrier[counter][1] = (int)obstacles.get(i).getY()+3;
					counter++;
				}
				if ((int)obstacles.get(i).getX()-2< 20 && (int)obstacles.get(i).getX()-2>= 0 && (int)obstacles.get(i).getY()-3< 20 && (int)obstacles.get(i).getY()-3>= 0)
				{
					barrier[counter][0] = (int)obstacles.get(i).getX()-2;
					barrier[counter][1] = (int)obstacles.get(i).getY()-3;
					counter++;
				}*/
			}	
		}
		
		path = new ArrayList<Node>();
		for (int i =0; i<waypoints.size()-1; i++)
		{
			AStar astar;
			if(waypoints.get(i).getOrientation() == Orientation.NORTH)
			{
				astar = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier, ori, South);
			}
			else if(waypoints.get(i).getOrientation() == Orientation.SOUTH)
			{
				astar = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier, ori, North);
			}
			else if(waypoints.get(i).getOrientation() == Orientation.EAST)
			{
				astar = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier, ori, West);
			}
			else
			{
				astar = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier, ori, East);
			}
			astar.display();
			astar.process();
			path = astar.displaySolution();
			ori = path.get(0).orientation;
			for (int j = path.size()-2; j>=0; j--)
			{
				Node temp = path.get(j);
				System.out.print(temp.movement);
				plannedmovement += temp.movement;
			}
			System.out.println();
		}
		steps = new ArrayList<String>(Arrays.asList(plannedmovement.split(",")));

		System.out.println(steps);
	}
		
	/*for(Node n : path)
		{
			System.out.println(n.toString());
			Node n1 = n;
			while(n1.parent != null)
			{
				System.out.println(n1.parent.toString());
				n1 = n1.parent;
			}
		}
		
		generateCommands(path.get(current_path));
		
		
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.TURN, -Math.PI/2));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.TURN, Math.PI/2));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		commands.add(new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
		
	}*/
	
	/*void generateCommands(Node node)
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
			/*if(node.parent.parent == null)
			{
				prev_dir = this.direction;
				//generateTurnCommands(curr_dir, prev_dir);
				node = node.parent;
				continue;
			}
			*/
			//else
			//prev_dir = getNodeDirection(node.parent, node.parent.parent);
			
			//generateTurnCommands(curr_dir, prev_dir);
			//commands.add(0, new Command(CommandType.MOVE, SimulatorS.getBlockSize()));
			/*moveByNodeDir(node, node.parent);
			node = node.parent;
		}
		System.out.println("~ Command: " + current_path);
		for(Command c : commands)
			System.out.println(c.toString());
	}*/
	
	/*void moveByNodeDir(Node curr, Node prev)
	{
		int x = 0, y = 0;
		// move west
		if(curr.i < prev.i)
		{
			x = -1;
		}
		// move east
		else if(curr.i > prev.i)
		{
			x = 1;
		}
		// move north
		else if(curr.j > prev.j)
		{
			y = 1;
		}
		// move south
		else if(curr.j > prev.j)
		{
			y = -1;
		}
		else
			return;
		
		commands.add(0, new Command(CommandType.MOV2, x*SimulatorS.getBlockSize(), y*SimulatorS.getBlockSize()));
		
	}*/
	
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
		
		if(steps.size() != 0)
		{

			String nextStep = steps.get(0);
			if (nextStep.equals("F"))
			{
				if (dir == North)
				{
					y+=30;
				}
				else if(dir == South)
				{
					y-=30;
				}
				else if(dir == West)
				{
					x -=30;
				}
				else if (dir== East)
				{
					x += 30;
				}
				steps.remove(0);
			}
			else if (nextStep.equals("R"))
			{
				if (dir == North)
				{
					y-=30;
				}
				else if(dir == South)
				{
					y+=30;
				}
				else if(dir == West)
				{
					x +=30;
				}
				else if (dir== East)
				{
					x -= 30;
				}
				steps.remove(0);
			}
			else if (nextStep.equals("FL"))
			{
				if (stage == 0 || stage == 2){
					if (dir == North)
					{
						y+=30;
						stage++;	
					}
					else if(dir == South)
					{
						y-=30;
						stage++;
					}
					else if(dir == West)
					{
						x -=30;
						stage++;
					}
					else if (dir== East)
					{
						x += 30;
						stage++;
					}
				}
				else if (stage == 1){
					if (dir == North)
					{
						setDirection(Math.PI);
						dir = West;
						stage++;
					}
					else if(dir == South)
					{
						setDirection(0.0);
						dir = East;
						stage++;
					}
					else if(dir == West)
					{
						setDirection(-Math.PI/2);
						dir = South;
						stage++;
					}
					else if (dir== East)
					{
						setDirection(Math.PI/2);
						dir = North;
						stage++;
					}

					
				}
				if (stage == 3){
					stage = 0;
					steps.remove(0);
				}
			}
			else if (nextStep.equals("FR"))
			{
				if (stage == 0 || stage == 2){
					if (dir == North)
					{
						y+=30;
						stage++;	
					}
					else if(dir == South)
					{
						y-=30;
						stage++;
					}
					else if(dir == West)
					{
						x -=30;
						stage++;
					}
					else if (dir== East)
					{
						x += 30;
						stage++;
					}
				}
				else if (stage == 1){
					if (dir == North)
					{
						setDirection(0.0);
						dir = East;
						stage++;
					}
					else if(dir == South)
					{
						setDirection(Math.PI);
						dir = West;
						stage++;
					}
					else if(dir == West)
					{
						setDirection(Math.PI/2);
						dir = North;
						stage++;
					}
					else if (dir== East)
					{
						setDirection(-Math.PI/2);
						dir = South;
						stage++;
					}

					
				}
				if (stage == 3){
					stage = 0;
					steps.remove(0);
				}
			}
			/*else if (nextStep.equals("RR"))
			{
				if (stage == 0)
				{
					if (dir == North)
					{
						setDirection(Math.PI);
						dir = West;
						stage++;
					}
					else if(dir == South)
					{
						setDirection(0.0);
						dir = East;
						stage++;
					}
					else if(dir == West)
					{
						setDirection(-Math.PI/2);
						dir = South;
						stage++;
					}
					else if (dir== East)
					{
						setDirection(Math.PI/2);
						dir = North;
						stage++;
					}
				}
				else if (stage == 1||stage == 2||stage == 3)
				{
					if (dir == North)
					{
						y-=30;
						stage++;	
					}
					else if(dir == South)
					{
						y+=30;
						stage++;
					}
					else if(dir == West)
					{
						x +=30;
						stage++;
					}
					else if (dir== East)
					{
						x -= 30;
						stage++;
					}
				}
				if (stage == 4){
					stage = 0;
					steps.remove(0);
				}
			}
			else if (nextStep.equals("RL"))
			{
				if (stage == 0)
				{
					if (dir == North)
					{
						setDirection(0.0);
						dir = East;
						stage++;
					}
					else if(dir == South)
					{
						setDirection(Math.PI);
						dir = West;
						stage++;
					}
					else if(dir == West)
					{
						setDirection(Math.PI/2);
						dir = North;
						stage++;
					}
					else if (dir== East)
					{
						setDirection(-Math.PI/2);
						dir = South;
						stage++;
					}
				}
				else if (stage == 1||stage == 2||stage == 3)
				{
					if (dir == North)
					{
						y-=30;
						stage++;	
					}
					else if(dir == South)
					{
						y+=30;
						stage++;
					}
					else if(dir == West)
					{
						x +=30;
						stage++;
					}
					else if (dir== East)
					{
						x -= 30;
						stage++;
					}
				}
				if (stage == 4){
					stage = 0;
					steps.remove(0);
				}
			}*/
		}

	}
			//return;
		
		/*Command currCmd = commands.get(current_command);
		
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
			case MOV2:
				x += currCmd.arg1;
				y += currCmd.arg2;
				break;
			default:
				break;
		}
		
		// when reached waypoint, current_wp++
		if(current_command+1 >= commands.size())
		{

			//SimulatorManager.getInstance().timer.stop();
			
			current_command = 0;
			if(current_path+1 >= path.size())
			{
				SimulatorManager.getInstance().timer.stop();
			}
			else
			{
				current_path++;
				generateCommands(path.get(current_path));
			}
			
		}
		else
		{
			current_command++;
		}*/

}
