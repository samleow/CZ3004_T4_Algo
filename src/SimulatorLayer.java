package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.Position.Orientation;

public class SimulatorLayer
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
	
	public SimulatorLayer(List<Position> Obstacle)
	{
		// Main frame
		JFrame frame = new JFrame("Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Window default size
		frame.setSize(850,900);
		// Layout of grid
		frame.setLayout(new BorderLayout());
		
		// Grid Panel in the center
		JLayeredPane gridPanel = new JLayeredPane();
		int size_x = 39, size_y = 39;
		int margin_x = 1, margin_y = 1;
		//gridPanel.setLayout(new GridLayout(20,20,1,1));
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
		
		Image img = null;
		Image img_car = null;
		try {
			// hardcoded file location !
			img = retrieveImage("/src/resources/arrow.png");
			img_car = retrieveImage("/src/resources/car.png");
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
				panel.setBounds(i*(size_x+margin_x),j*(size_y+margin_y),size_x, size_y);
				grid.get(0).add(panel);
				gridPanel.add(panel, JLayeredPane.DEFAULT_LAYER);
			}
		}
		
		// car
		ImagePanel car = new ImagePanel(img_car);
		car.setBounds(0, 17*(size_y+margin_y), size_x*3, size_y*3);
		//car.setBackground(Color.cyan);
		car.setOpaque(false);
		car.render = true;
		car.theta = 0;
		gridPanel.add(car, JLayeredPane.PALETTE_LAYER); 
		
		// Robot starting position
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<4; j++)
			{
				grid.get(i).get(j).setBackground(Color.YELLOW);
			}
		}
		// Obstacle positions
		this.obstacles = Obstacle;
		for (Position o : obstacles)
		{
			grid.get((int)o.y).get((int)o.x).setBackground(Color.RED);
			
			if (o.orientation == Orientation.EAST)
			{
				grid.get((int)o.y).get((int)o.x).theta += Math.PI/2;
			}
			else if (o.orientation == Orientation.WEST)
			{
				grid.get((int)o.y).get((int)o.x).theta -= Math.PI/2;
			}
			else if (o.orientation == Orientation.SOUTH)
			{
				grid.get((int)o.y).get((int)o.x).theta += Math.PI;
			}
			grid.get((int)o.y).get((int)o.x).render = true;
		}
		
		// TODO: have actual car position and proper updates
		// button actions
		button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	car.theta -= Math.PI/2;
        		
            	frame.repaint();
            }
        });
		button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	car.theta += Math.PI/2;
            	frame.repaint();
            }
        });
		button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	car.setLocation(car.getBounds().x + size_x+margin_x, car.getBounds().y);
            	frame.repaint();
            }
        });
		button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	car.setLocation(car.getBounds().x - size_x-margin_x, car.getBounds().y);
            	frame.repaint();
            }
        });
		button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	car.setLocation(car.getBounds().x, car.getBounds().y - size_y -margin_y);		//negative because grid is alr at 17 boxes from the top
            	frame.repaint();
            }
        });
		button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	car.setLocation(car.getBounds().x, car.getBounds().y+size_y+margin_y);
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
	
	
	  //public static void main(String[] args) { new SimulatorLayer(); }
	 
}