package src.rework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.CarPosition;
import src.Position;
import src.AStar.AStar;
import src.AStar.Node;
import src.Position.Orientation;

public class Robot extends SimObject
{
	// Might need to store list of commands here

	private static Robot _instance = null;
	public int[][] barrier = new int[500][2];
	private static final int North=1, East= 2, South = 3, West =4;
	List<CarPosition> waypoints = null;
	public List<Position> obstacles = null;
	List<Node> path = null;
	int ori = North;
	String plannedmovement = "";
	ArrayList<String> steps;
	int dir = ori;
	int stage =0;
	Boolean onspotright;
	Boolean onspotleft;
	int current_wp = 0;
	int current_command = 0;
	int current_path = 0;
	boolean onspot;
	boolean contingencycheck;
	int finalori;
	
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
			/*if (obstacles.get(i).getOrientation() == Orientation.NORTH)
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
				
			}*/	
		}
		
		path = new ArrayList<Node>();
		for (int i =0; i<waypoints.size()-1; i++)
		{
			AStar astar;
			astar = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier, ori);
			astar.display();
			astar.process();
			path = astar.displaySolution();
			int k = 1;
			onspot = false;
			contingencycheck = false;
			while (path == null) //path is empty array list
			{
				if (waypoints.get(i).getX() == waypoints.get(i+1).getX() && waypoints.get(i).getY() == waypoints.get(i+1).getY())
				{
					onspot = true;
					switch (ori)
					{
						//North
						case 1:
						{
							if (waypoints.get(i+1).getOrientation() == Orientation.EAST)
							{
								//add turn left
								plannedmovement += "u,S,";
								ori = 4;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.WEST)
							{
								//add turn right
								plannedmovement += "i,S,";
								ori = 2;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.NORTH)
							{
								//turn 180
								plannedmovement += "u,u,S,";
								ori = 3;
							}
							break;
						}
						//East
						case 2:
						{
							if (waypoints.get(i+1).getOrientation() == Orientation.NORTH)
							{
								//add turn left
								plannedmovement += "u,S,";
								ori = 3;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH)
							{
								//add turn right
								plannedmovement += "i,S,";
								ori = 1;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.EAST)
							{
								//turn 180
								plannedmovement += "u,u,S,";
								ori = 4;
							}
							break;
						}
						//South
						case 3:
						{
							if (waypoints.get(i+1).getOrientation() == Orientation.WEST)
							{
								//add turn left
								plannedmovement += "u,S,";
								ori = 2;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.EAST)
							{
								//add turn right
								plannedmovement += "i,S,";
								ori = 4;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH)
							{
								//turn 180
								plannedmovement += "u,u,S,";
								ori = 1;
							}
							break;
						}
						//West
						case 4:
						{
							if (waypoints.get(i+1).getOrientation() == Orientation.NORTH)
							{
								//add turn left
								plannedmovement += "u,S,";
								ori = 3;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH)
							{
								//add turn right
								plannedmovement += "i,S,";
								ori = 1;
							}
							else if (waypoints.get(i+1).getOrientation() == Orientation.WEST)
							{
								//turn 180
								plannedmovement += "u,u,S,";
								ori = 2;
							}
							break;
						}
					}
				}
				switch (ori)
				{
					//North
					case 1:
					{
						if (k == 1)
						{
							ori = 4;
							//add the on spot movement to planned movement
							//turn left
							plannedmovement += "u,";
						}
						else
						{
							ori = 2;
							//add the on spot movement to planned movement
							//turn right
							plannedmovement += "i,";
						}
						break;
					}
					//East
					case 2:
					{
						if (k==1)
						{
							ori = 1;
							//add the on spot movement to planned movement
							//turn right
							plannedmovement += "u,";
						}
						else
						{
							ori = 3;
							//add the on spot movement to planned movement
							//turn left
							plannedmovement += "i,";
						}
						break;
					}
					//South
					case 3:
					{
						if (k== 1)
						{
							ori = 4;
							//add the on spot movement to planned movement
							//turn right
							plannedmovement += "i,";
						}
						else
						{
							ori = 2;
							//add the on spot movement to planned movement
							//turn left
							plannedmovement += "u,";
						}
						break;
					}
					//West
					case 4:
					{
						if (k== 1)
						{
							ori = 3;
							//add the on spot movement to planned movement
							//turn left
							plannedmovement += "u,";
						}
						else
						{
							ori = 1;
							//add the on spot movement to planned movement
							//turn right
							plannedmovement += "i,";
						}
						break;
					}
					
				}
				AStar contingency = new AStar(20, 20, (int)waypoints.get(i).getX(), (int)waypoints.get(i).getY(), (int)waypoints.get(i+1).getX(), (int)waypoints.get(i+1).getY(), barrier, ori);
				contingency.display();
				contingency.process();
				path = contingency.displaySolution();
				contingencycheck = true;
                finalori = contingency.FinalOrientation();
                if (k == 4){
                    finalori = ori;
                    break;
                }
				k+=1;
			}
			if (onspot)
            {
                steps = new ArrayList<String>(Arrays.asList(plannedmovement.split(",")));
				plannedmovement = "";
				return;
            }
			if (path != null)
            {
                ori = path.get(0).orientation;
                for (int j = path.size() - 2; j >= 0; j--) {
                    Node temp = path.get(j);
                    plannedmovement += temp.movement;
                }
            }
			if (!contingencycheck)
            {
                finalori = astar.FinalOrientation();
            }
            //North = 1
            if (finalori == 1){
                if (waypoints.get(i+1).getOrientation() == Orientation.WEST) {
                    plannedmovement += "i,S,u,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.EAST) {
                    plannedmovement += "u,S,i,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH){
                    plannedmovement += "S,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.NORTH){
                    plannedmovement += "u,u,S,i,i,";
                }
            }
            //East = 2
            else if (finalori == 2)
            {
                if (waypoints.get(i+1).getOrientation() == Orientation.NORTH) {
                    plannedmovement += "i,S,u,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH) {
                    plannedmovement += "u,S,i,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.WEST){
                    plannedmovement += "S,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.EAST){
                    plannedmovement += "u,u,S,i,i,";
                }
            }
            //South = 3
            else if (finalori == 3)
            {
                if (waypoints.get(i+1).getOrientation() == Orientation.EAST) {
                    plannedmovement += "i,S,u,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.WEST) {
                    plannedmovement += "u,S,i,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.NORTH){
                    plannedmovement += "S,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH){
                    plannedmovement += "u,u,S,i,i,";
                }
            }
            //West = 4
            else if (finalori == 4)
            {
                if (waypoints.get(i+1).getOrientation() == Orientation.SOUTH) {
                    plannedmovement += "i,S,u,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.NORTH) {
                    plannedmovement += "u,S,i,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.EAST){
                    plannedmovement += "S,";
                }
                else if (waypoints.get(i+1).getOrientation() == Orientation.WEST){
                    plannedmovement += "u,u,S,i,i,";
                }
            }
			//plannedmovement += "S,";
			System.out.print(plannedmovement);
			System.out.println();
		}
		steps = new ArrayList<String>(Arrays.asList(plannedmovement.split(",")));

		System.out.println(steps);
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
	
	/*void generateTurnCommands(double target_dir, double prev_dir)
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
	}*/
	
	public void update()
	{
		
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
				if (stage == 0 || stage == 1||stage == 3||stage == 4||stage== 5){
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
				else if (stage == 2){
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
				if (stage == 6){
					stage = 0;
					steps.remove(0);
				}
			}
			else if (nextStep.equals("FR"))
			{
				if (stage == 0 || stage == 1||stage == 3||stage == 4||stage==5){
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
				else if (stage == 2){
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
				if (stage == 6){
					stage = 0;
					steps.remove(0);
				}
			}
			else if (nextStep.equals("S")){
				x+=0;
				y+=0;
				steps.remove(0);
			}
			else if (nextStep.equals("u")){
				if (dir == North)
				{
					setDirection(Math.PI);
					dir = West;

				}
				else if(dir == South)
				{
					setDirection(0.0);
					dir = East;

				}
				else if(dir == West)
				{
					setDirection(-Math.PI/2);
					dir = South;

				}
				else if (dir== East)
				{
					setDirection(Math.PI/2);
					dir = North;

				}
				steps.remove(0);
			}
			else if (nextStep.equals("i")){
				if (dir == North)
				{
					setDirection(0.0);
					dir = East;

				}
				else if(dir == South)
				{
					setDirection(Math.PI);
					dir = West;

				}
				else if(dir == West)
				{
					setDirection(Math.PI/2);
					dir = North;

				}
				else if (dir== East)
				{
					setDirection(-Math.PI/2);
					dir = South;
				}
				steps.remove(0);
			}
		}
	}
			
}
