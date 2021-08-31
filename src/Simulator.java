package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.Position.Orientation;

public class Simulator
{
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
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if(render)
			{
				Graphics2D g2d = (Graphics2D) g;
				g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
		        g2d.rotate(theta);
		        g2d.translate(-img.getWidth(this) / 2, -img.getHeight(this) / 2);
				g.drawImage(img, 0, 0, null);
			}
		}
		
	}
	
	// Nested List of Panels as a grid
	// Grid position based on grid.get(y).get(x)
	private List<List<ImagePanel>> grid = new ArrayList<>();
	private List<Position> obstacles = new ArrayList<Position>();
	
	public Simulator()
	{		
		// Main frame
		JFrame frame = new JFrame("Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Window default size
		frame.setSize(800,800);
		// Layout of grid
		frame.setLayout(new BorderLayout());
		
		// Grid Panel in the center
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(20,20,1,1));
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
		
		Image img = null;
		try {
			// hardcoded file location !
			img = retrieveImage("/src/resources/arrow.png");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// Grid init
		for(int i=19; i>=0; i--)
		{
			grid.add(0, new ArrayList<ImagePanel>());
			for(int j=19; j>=0; j--)
			{
				ImagePanel panel = new ImagePanel(img);
				// Setting grid default color
				panel.setBackground(Color.BLACK);
				grid.get(0).add(panel);
				gridPanel.add(panel);
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
		// TODO: implement obstacle positions from io
		obstacles.add(new Position(1, 1, Orientation.WEST));
		obstacles.add(new Position(8, 5, Orientation.NORTH));
		obstacles.add(new Position(7, 3, Orientation.EAST));
		obstacles.add(new Position(3, 3, Orientation.WEST));
		for (Position o : obstacles)
		{
			grid.get((int)o.y).get((int)o.x).setBackground(Color.RED);
			grid.get((int)o.y).get((int)o.x).render = true;
		}
		
		// TODO: have actual car position and proper updates
		// button actions
		button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	grid.get(1).get(1).theta -= Math.PI/2;
            	frame.repaint();
            }
        });
		button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	grid.get(1).get(1).theta += Math.PI/2;
            	frame.repaint();
            }
        });
		
		frame.setVisible(true);
	}
	
	Image retrieveImage(String path) throws IOException
	{
		try {
			return ImageIO.read(Simulator.class.getResource(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Simulator();
	}
}
