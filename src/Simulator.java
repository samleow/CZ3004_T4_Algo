package src;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Simulator
{
	// Nested List of Panels as a grid
	private List<List<JPanel>> grid = new ArrayList<>();
	
	public Simulator()
	{
		// Main frame
		JFrame frame = new JFrame();
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
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				grid.get(i).get(j).setBackground(Color.YELLOW);
			}
		}
		// testing grid position
		grid.get(10).get(15).setBackground(Color.RED);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Simulator();
	}
}
