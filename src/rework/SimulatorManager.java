package src.rework;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import src.CarPosition;
import src.HamiltonianPathSimulator;
import src.Position;


public class SimulatorManager extends JPanel
{
	List<Obstacle> obstacles = new ArrayList<Obstacle>();
	int block_size = 30;
	boolean is_running = false;
	Robot robot;
	
	public Timer timer;
	int timer_delay = 200;
	
	private static SimulatorManager _instance;
	
	private SimulatorManager()
	{
		block_size = SimulatorS.getBlockSize();
		robot = Robot.getInstance();
		robot.moveTo(0.0, 0.0);
		robot.setDimension(block_size*3, block_size*3);
		robot.setDirection(Math.PI/2);
		
	}
	
	public static SimulatorManager getInstance()
	{
		if(_instance == null)
		{
			_instance = new SimulatorManager();
		}
		
		return _instance;
	}
		
	public void setUpObstacles(List<Position> obs)
	{
		// algo path planning here
		HamiltonianPathSimulator h = new HamiltonianPathSimulator(obs);
		robot.waypoints = h.getCarPositions();
		
		/*for(CarPosition wp : robot.waypoints)
		{
			wp.setvisited(false);
		}*/
		
		for(Position o : obs)
		{
			obstacles.add(new Obstacle(o.getX()*block_size, o.getY()*block_size, block_size, block_size, o.getDirection()));
		}
	}
	
	void drawGrid(Graphics2D g)
	{
		int block_size = SimulatorS.getBlockSize();
		for (int i = 1; i < 20; i++)
		{
			//obstacles.add(new Obstacle(i*block_size, j*block_size, block_size, block_size, 0.0));
			g.drawLine(i*block_size, 0, i*block_size, 20*block_size);
			g.drawLine(0, i*block_size, 20*block_size, i*block_size);
		}
	}
	
	// Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
        g2d.scale(1, -1);
        g2d.translate(0, -getHeight());
        
		drawGrid(g2d);
		
		for (SimObject e : obstacles) {
            g2d.drawImage(e.getImage(), e.getAffineTransform(), this);
        }

        g2d.drawImage(robot.getImage(), robot.getAffineTransform(), this);
        
		g.dispose();
	}
	
	public void runSimulation()
	{
		// update robot here
		if(is_running)
			return;
		
		// path planning here
		robot.planRoute();
		
		timer = new Timer(timer_delay, simulationUpdate);
		timer.start();
		is_running = true;
	}
	
	ActionListener simulationUpdate = new ActionListener()
	{
		public void actionPerformed(ActionEvent evt)
		{
			robot.update();
			
			repaint();
		}
	};
	
}
