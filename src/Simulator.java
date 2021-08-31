package src;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.Position.Orientation;

public class Simulator
{
	// Nested List of Panels as a grid
	// Grid position based on grid.get(y).get(x)
	private List<List<JPanel>> grid = new ArrayList<>();
	private List<Position> obstacles = new ArrayList<Position>();
	
	public Simulator()
	{
		// Main frame
		JFrame frame = new JFrame("Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Window default size
		frame.setSize(800,800);
		// Layout of grid
		frame.setLayout(new GridLayout(20,20,1,1));
		
		// Grid init
		for(int i=19; i>=0; i--)
		{
			grid.add(0, new ArrayList<JPanel>());
			for(int j=19; j>=0; j--)
			{
				JPanel panel = new JPanel();
				// Setting grid default color
				panel.setBackground(Color.BLACK);
				grid.get(0).add(panel);
				frame.add(panel);
			}
		}
		
		// Robot starting position
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
				grid.get(i).get(j).setBackground(Color.YELLOW);
			}
		}
		// Obstacle positions
		obstacles.add(new Position(1, 1, Orientation.WEST));
		obstacles.add(new Position(8, 5, Orientation.NORTH));
		obstacles.add(new Position(7, 3, Orientation.EAST));
		obstacles.add(new Position(3, 3, Orientation.WEST));
		for (Position o : obstacles)
		{
			grid.get((int)o.y).get((int)o.x).setBackground(Color.RED);
		}
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Simulator();
	}
}
