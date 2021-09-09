package src.rework;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.Position;
import src.readinput;

public class SimulatorS extends JFrame
{
	public static final int SCREEN_WIDTH = 750;
	public static final int SCREEN_HEIGHT = 700;
	public static final int GRID_SIZE = 600;
	public static final int GRID_MARGIN = 1;
	
	public SimulatorS()
	{
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Simulator");
		setResizable(true);
		setLayout(new BorderLayout());
		
		add(SimulatorManager.getInstance(), BorderLayout.CENTER);
		
		// Buttons Panel at the bottom
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());
		add(buttonsPanel, BorderLayout.PAGE_END);
		
		// Button for simulation
		JButton button_run_sim = new JButton("Run Simulation");
		buttonsPanel.add(button_run_sim);
		button_run_sim.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// run algorithm to get shortest path
				
				// run simulation
				SimulatorManager.getInstance().runSimulation();
			}
		});
		
		setVisible(true);
	}
	
	public static int getBlockSize()
	{
		return (int)Math.floor(GRID_SIZE/20);
	}
	
	public static void main(String[] args)
	{
		// read input file and get list of obstacles
		// can call SimulatorManager.getInstance().setUpGrid(Positions)
		List<Position> obstacles = new readinput().getObstacles();
		SimulatorManager.getInstance().setUpObstacles(obstacles);
		
		new SimulatorS();
	}

}
