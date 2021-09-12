package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.lang.model.util.ElementScanner14;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import src.AStar.AStar;
//import jdk.internal.agent.resources.agent;
import src.Position.Orientation;

public class SimulatorLayer
{
	// Timer delay
	int delay = 500;
	// Grid pixel sizes
	int size_x = 29, size_y = 29;
	int margin_x = 1, margin_y = 1;
	// Robot car's rotation radius
	double rot_radius = 2.5;
	// Nested List of Panels as a grid
	// Grid position based on grid.get(y).get(x)
	private List<List<ImagePanel>> grid = new ArrayList<>();
	private List<Position> obstacles = new ArrayList<Position>();
	private List<CarPosition> positions = new ArrayList<CarPosition>();
	private String bypasspath = "\0";
	private JFrame frame = null;
	private ImagePanel car;
	private int[][] barrier = new int[500][2];
	private static final int North=1, East= 2, South = 3, West =4;

	class ImagePanel extends JPanel
	{
		Image img = null;
		public boolean render = false;
		// in radians
		public double theta = 0.0;

		public ImagePanel(Image img)
		{
			super();
			this.img = img;
		}
		
		public ImagePanel(ImagePanel imgpnl)
		{
			super();
			this.setBounds(imgpnl.getBounds());
			this.img = imgpnl.img;
		}
		
		public Orientation getOrientation()
		{
			if(theta == 0.0)
				return Orientation.NORTH;
			else if(theta == Math.PI/2)
				return Orientation.EAST;
			else if(theta == -Math.PI/2)
				return Orientation.WEST;
			else if(theta == Math.PI)
				return Orientation.SOUTH;
			else if(theta == -Math.PI)
			{
				theta = Math.PI;
				return Orientation.SOUTH;
			}
			else return null;
		}
		
		public void setOrientation(Orientation dir)
		{
			switch(dir)
			{
				case NORTH:
					theta = 0.0;
					break;
				case EAST:
					theta = Math.PI/2;
					break;
				case WEST:
					theta = -Math.PI/2;
					break;
				case SOUTH:
					theta = Math.PI;
					break;
				default:
					break;
			}
		}
		
		// overload
		public void setOrientation(int dir)
		{
			switch(dir)
			{
				case 0:
					theta = 0.0;
					break;
				case 1:
					theta = Math.PI/2;
					break;
				case 2:
					theta = -Math.PI/2;
					break;
				case 3:
					theta = Math.PI;
					break;
				default:
					break;
			}
		}
		
		public Point getGridLocation()
		{			
			return new Point((int)Math.floor(this.getX()/(size_x+margin_x)), (int)Math.floor(this.getY()/(size_y+margin_y)));
		}

		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if (render)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
				g2d.rotate(theta);
				g2d.translate(-img.getWidth(this) / 2, -img.getHeight(this) / 2);
				g.drawImage(img, 0, 0, null);
			}
		}

	}

	public SimulatorLayer(List<Position> Obstacle)
	{
		// Main frame
		frame = new JFrame("Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Window default size
		frame.setSize(750, 700);
		// Layout of grid
		frame.setLayout(new BorderLayout());

		// Grid Panel in the center
		JLayeredPane gridPanel = new JLayeredPane();
		// gridPanel.setLayout(new GridLayout(20,20,1,1));
		gridPanel.setBounds(0, 0, 20 * (size_x + margin_x), 20 * (size_y + margin_y));
		frame.add(gridPanel, BorderLayout.CENTER);

		// Buttons Panel at the bottom
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		frame.add(buttonsPanel, BorderLayout.PAGE_END);

		// Buttons
		JButton button = new JButton("Turn left.");
		buttonsPanel.add(button);
		JButton button2 = new JButton("Turn right.");
		buttonsPanel.add(button2);
		JButton button3 = new JButton("Move right.");
		buttonsPanel.add(button3);
		JButton button4 = new JButton("Move left.");
		buttonsPanel.add(button4);
		JButton button5 = new JButton("Move up.");
		buttonsPanel.add(button5);
		JButton button6 = new JButton("Move down.");
		buttonsPanel.add(button6);
		JButton button_run_sim = new JButton("Run Simulation");
		buttonsPanel.add(button_run_sim);

		Image img = null;
		Image img_car = null;
		try
		{
			// hardcoded file location !
			img = retrieveImage("/src/resources/arrow.png");
			img_car = retrieveImage("/src/resources/car.png");
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// Grid init
		for (int i = 19; i >= 0; i--)
		{
			grid.add(0, new ArrayList<ImagePanel>());
			for (int j = 19; j >= 0; j--)
			{
				ImagePanel panel = new ImagePanel(img);
				// Setting grid default color
				panel.setBackground(Color.BLACK);
				panel.setBounds(i * (size_x + margin_x), j * (size_y + margin_y), size_x, size_y);
				grid.get(0).add(panel);
				gridPanel.add(panel, JLayeredPane.DEFAULT_LAYER);
			}
		}
		
		// car
		car = new ImagePanel(img_car);
		car.setBounds(0, 17 * (size_y + margin_y), size_x * 3, size_y * 3);
		// car.setBackground(Color.cyan);
		car.setOpaque(false);
		car.render = true;
		car.theta = 0;
		gridPanel.add(car, JLayeredPane.PALETTE_LAYER);

		// Robot starting position
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				grid.get(i).get(j).setBackground(Color.YELLOW);
			}
		}
		// Obstacle positions
		this.obstacles = Obstacle;
		for (Position o : obstacles)
		{
			grid.get((int) o.x).get((int) o.y).setBackground(Color.RED);

			if (o.orientation == Orientation.EAST)
			{
				grid.get((int) o.x).get((int) o.y).theta += Math.PI / 2;
			}
			else if (o.orientation == Orientation.WEST)
			{
				grid.get((int) o.x).get((int) o.y).theta -= Math.PI / 2;
			}
			else if (o.orientation == Orientation.SOUTH)
			{
				grid.get((int) o.x).get((int) o.y).theta += Math.PI;
			}
			grid.get((int) o.x).get((int) o.y).render = true;
		}

		// TODO: have actual car position and proper updates
		// button actions
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				car.theta -= Math.PI / 2;

				frame.repaint();
			}
		});
		button2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				car.theta += Math.PI / 2;
				frame.repaint();
			}
		});
		button3.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				car.setLocation(car.getBounds().x + size_x + margin_x, car.getBounds().y);
				frame.repaint();
			}
		});
		button4.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				car.setLocation(car.getBounds().x - size_x - margin_x, car.getBounds().y);
				frame.repaint();
			}
		});
		button5.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				car.setLocation(car.getBounds().x, car.getBounds().y - size_y - margin_y); // negative because grid is
																							// alr at 17 boxes from the
																							// top
				frame.repaint();
			}
		});
		button6.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				car.setLocation(car.getBounds().x, car.getBounds().y + size_y + margin_y);
				frame.repaint();
			}
		});
		button_run_sim.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				runSimulation();
			}
		});

		
		frame.setVisible(true);
	}

	Image retrieveImage(String path) throws IOException
	{
		try
		{
			return ImageIO.read(Simulator.class.getResource(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	ActionListener taskPerformer = new ActionListener()
	{

		// TODO: clean up
		//boolean is_visited[] = {false,false,false,false,false};
		int index = 1;
		
		//ImagePanel car_ghost;
		
		public void actionPerformed(ActionEvent evt)
		{
			if(index>=positions.size())
				return;
			
			//car_ghost = new ImagePanel(car);
			
			if(!positions.get(index).visited)
			{				
				if(moveToPosition(positions.get(index)))
				{
					positions.get(index).visited = true;
					if(index<positions.size())
						index++;
					//return;
				}
			}
			
			/*moveToPosition(obstacles.get(0));
			
			System.out.println(index);
			System.out.println("car pos " + car.getLocation());
			System.out.println("car ghost " + car_ghost.getLocation());
			index++;
			*/
			
			frame.repaint();
		}
	};
	
	// checks collision for a single block of movement
	//		if can need check for multiple blocks of movement
	// direction : direction the car is moving to
	// return 0 if no collision
	// return 1 if path on collision while moving right
	// return 2 if path on collision while moving left
	// return 3 if path on collision while moving up
	// TODO
	/*int checkCollision(Orientation direction, Position target,double x, double y)
	{
		// collision check of target on original path
		
		// if have collision, collision check on altered path
		// maybe can use recursive
		//for(Position obs: obstacles)
		//{
			
		//}
		//double x = Math.floor(car.getLocation().x/(size_x + margin_x));
		//double y = Math.floor(17 - car.getLocation().y/(size_y + margin_y));
		if (direction == Orientation.EAST)
		{
			if (((x + 4)> target.x-2) && ((y>(target.y-5))&&(y<(target.y+3))))
			{
				return 1;
			}

		}
		else if (direction == Orientation.WEST)
		{
			if(((x - 1)< target.x+3) && ((y>(target.y-5))&&(y<(target.y+3))))
			{
				return 2;
			}
		}
		else if (direction == Orientation.NORTH)
		{
			if(((y + 4)> target.y-2) && ((x>(target.x-5))&&(x<(target.x+3))))
			{
				return 3;
			}
		}
		else if (direction == Orientation.SOUTH)
		{
			if(((y - 1)< target.y+3) && ((x>(target.x-5))&&(y<(target.y+3))))
			{
				return 4;
			}
		}

		return 0;
	}*/
	/*int clearanceCheck(int c, double a, double b)
	{
		boolean obstructedleft = false;
		boolean obstructedright = false;
		double g =a;
		double h = b;
		int x = 0;
		switch(c){
			case 1:
				for (int i = 0; i<4;i++)
				{
					for (Position obs: obstacles)
					{
						
						x = checkCollision(Orientation.NORTH, obs, g, h);
						if (x != 0)
						{
							obstructedleft = true;
							break;
						}
					}
					h++;
				}
				for (int i = 0; i<8;i++)
				{
					for (Position obs: obstacles)
					{
						x = checkCollision(Orientation.EAST, obs, g, h);
						if (x != 0)
						{
							obstructedleft = true;
							break;
						}
					}
					g++;
				}
				if (obstructedleft == true)
				{
					for (int i = 0; i<5;i++)
					{
						for (Position obs: obstacles)
						{
							x = checkCollision(Orientation.SOUTH, obs, a, b);
							if (x != 0)
							{
								obstructedright = true;
								break;
							}
							b--;
						}
					}
					for (int i = 0; i<8;i++)
					{
						for (Position obs: obstacles)
						{
							x = checkCollision(Orientation.EAST, obs, a, b);
							if (x != 0)
							{
								obstructedright = true;
								break;
							}
							a++;
						}
					}
				}
				if (obstructedleft != true)
				{
					return 1;
				}
					
				else if (obstructedleft == true && obstructedright != true)
				{
					return 2;
				}	
				else 
				{
					return 3;
				}
		}
		return c;
		 

	}*/
	
	// returns true if reached target position
	// else returns false
	boolean moveToPosition(Position target)
	{
		int x = 0, y = 0;
		double theta = 0.0;
		int cleared = 0;
		if(Math.floor(target.x) > Math.floor(car.getLocation().x/(size_x + margin_x)))
		{
			x = size_x + margin_x;
			theta = Math.PI/2;
			
		}
		// move left
		else if(Math.floor(target.x) < Math.floor(car.getLocation().x/(size_x + margin_x)))
		{
			x = -( size_x + margin_x);
			theta = -Math.PI/2;
			
		}
		// move down
		else if(Math.floor(target.y) < Math.floor(17 - (car.getLocation().y/(size_y + margin_y))))
		{
			y = size_y + margin_y;
			theta = Math.PI;
			
		}
		// move up
		else if(Math.floor(target.y) > Math.floor(17 - (car.getLocation().y/(size_y + margin_y))))
		{
			y = -(size_y + margin_y);
			theta = 0.0;			
		}
		else
		{
			// reached target
			car.setOrientation(Math.abs(3-target.orientation.ordinal()));
			return true;
		}
			
		car.setLocation(car.getBounds().x + x, car.getBounds().y + y);
		car.theta = theta;
		return false;
	}
	
	// -1 == error
	//  0 == same direction
	//  1 == opposite direction
	//  2 == facing perpendicular (might need to split)
	int checkForDirection(Position target)
	{
		/* // old code
		switch(target.orientation)
		{
		case NORTH:
			
			// car facing NORTH
			if(car.theta == 0.0)
			{
				
			}
			// car facing SOUTH
			else if(car.theta == Math.PI)
			{
				// Opposite direction
				
			}
			
			break;
		case EAST:
			break;
		case SOUTH:
			
			// car facing NORTH
			if(car.theta == 0.0)
			{
				// Opposite direction
				
			}
			// car facing SOUTH
			else if(car.theta == Math.PI)
			{
				
			}
			break;
		case WEST:
			break;
		default:
			break;
		}
		*/
		
		if(car.getOrientation() == null)
		{
			// car not oriented properly
			return -1;
		}
		
		if(target.orientation == car.getOrientation())
		{
			// Same direction
			return 0;
		}
		else if(target.orientation.ordinal() + car.getOrientation().ordinal() == 3)
		{
			// Opposite direction
			return 1;
		}
		else
		{
			// facing perpendicular
			// might need to split based on direction (pos/ neg theta)
			return 2;
		}
		
	}
	
	// Scenarios: (based on directional vector)
	// a:	direction along axis (straight line)
	// b:	forward left/ right
	// c:	backwards left/ right
	void movementForOppositeDirection(Position target)
	{
		Point car_gl = car.getGridLocation();
		// vector from car to target
		car_gl.x = (int)Math.floor(target.x - car_gl.x);
		car_gl.y = (int)Math.floor(target.y - car_gl.y);
		
		if(car_gl.x == 0 || car_gl.y == 0)
		{
			// Scenario a
			System.out.println(" ~ ~ Scenario a");
			return;
		}
		
		switch(car.getOrientation())
		{
			case NORTH:
				// forward
				if(car_gl.y < 0)
				{
					// Scenario b
					System.out.println(" ~ ~ Scenario b");
					
				}
				// backward
				else
				{
					// Scenario c
					System.out.println(" ~ ~ Scenario c");
					
				}
				break;
			case EAST:
				// forward
				if(car_gl.x > 0)
				{
					// Scenario b
					System.out.println(" ~ ~ Scenario b");
					
				}
				// backward
				else
				{
					// Scenario c
					System.out.println(" ~ ~ Scenario c");
					
				}
				break;
			case WEST:
				// forward
				if(car_gl.x < 0)
				{
					// Scenario b
					System.out.println(" ~ ~ Scenario b");
					
				}
				// backward
				else
				{
					// Scenario c
					System.out.println(" ~ ~ Scenario c");
					
				}
				break;
			case SOUTH:
				// forward
				if(car_gl.y > 0)
				{
					// Scenario b
					System.out.println(" ~ ~ Scenario b");
					
				}
				// backward
				else
				{
					// Scenario c
					System.out.println(" ~ ~ Scenario c");
					
				}
				break;
			default:
				System.out.println("Car is not oriented properly!");
				break;
		}
		
	}
	
	// moves car to position after rotation
	void rotateBy90(boolean left_dir)
	{
		switch(car.getOrientation())
		{
			case NORTH:
				if(left_dir)
				{
					car.setLocation(car.getBounds().x - (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y - (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = -Math.PI/2;
				}
				else
				{
					car.setLocation(car.getBounds().x + (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y - (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = Math.PI/2;
				}
				break;
			case EAST:
				if(left_dir)
				{
					car.setLocation(car.getBounds().x + (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y - (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = 0.0;
				}
				else
				{
					car.setLocation(car.getBounds().x + (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y + (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = Math.PI;
				}
				break;
			case WEST:
				if(left_dir)
				{
					car.setLocation(car.getBounds().x - (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y + (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = Math.PI;
				}
				else
				{
					car.setLocation(car.getBounds().x - (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y - (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = 0.0;
				}
				break;
			case SOUTH:
				if(left_dir)
				{
					car.setLocation(car.getBounds().x + (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y + (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = Math.PI/2;
				}
				else
				{
					car.setLocation(car.getBounds().x - (int)Math.floor(rot_radius*(size_x+margin_x)),
							car.getBounds().y + (int)Math.floor(rot_radius*(size_y+margin_y)));
					car.theta = -Math.PI/2;
				}
				break;
			default:
				break;
		}
	}
	
	public void runSimulation()
	{
		HamiltonianPathSimulator h = new HamiltonianPathSimulator(obstacles);
		int counter = 0;
		for (int i=0;i<obstacles.size();i++)
		{
			for (int j=0; j<4;j++)
			{
				for (int k= 0; k<4; k++)
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
		
		positions = h.getCarPositions();
		for (int i =0; i<positions.size()-1; i++)
		{
			AStar astar = new AStar(20, 20, (int)positions.get(i).x, (int)positions.get(i).y, (int)positions.get(i+1).x, (int)positions.get(i+1).y, barrier, North, South);
			astar.display();
			astar.process();
			astar.displaySolution();
		}
		
		for(CarPosition wp : positions)
		{
			wp.setvisited(false);
		}
		
		// pre-planning of turning path
		
		// debugging
		/*
		System.out.println(positions);
		
		Position test_p = new Position(-5,10,Orientation.WEST);
		System.out.println(" ~ ~ Car grid pos: " + car.getGridLocation());
		System.out.println(" ~ ~ Target grid pos: " + test_p);
		System.out.println(" ~ ~ Direction check: " + checkForDirection(test_p));
		movementForOppositeDirection(test_p);
		*/
		
		//rotateBy90(false);
		//frame.repaint();
		
		Timer timer = new Timer(delay, taskPerformer);
		timer.start();
	}
}

