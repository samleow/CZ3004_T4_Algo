package src.rework;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public abstract class SimObject
{
	protected double x, y;
    protected int width, height;
    protected double direction;
    protected Image image;
    
    protected SimObject()
    {
    	moveTo(0.0, 0.0);
    	setDimension(30, 30);
    	direction = 0.0;
    	image = null;
    }
    
    protected SimObject(double x, double y, int width, int height, double direction)
    {
    	moveTo(x, y);
    	setDimension(width, height);
    	this.direction = direction;
    	image = null;
    }
    
    public void moveTo(double x, double y)
    {
    	this.x = x;
    	this.y = y;
    }
    
    public void setDimension(int width, int height)
    {
    	this.width = width;
    	this.height = height;
    }
    
    public double getX()
    {
    	return x;
    }
    
    public double getY()
    {
    	return y;
    }
    
    public double getDirection()
    {
    	return direction;
    }
    
    public Image getImage()
    {
    	if(image == null)
    	{
    		// set image based on imageFileName()
    		String image_loc = "/src/resources/" + imageFileName();
            ImageIcon ii = new ImageIcon(getClass().getResource(image_loc));
            image = ii.getImage();
    	}
    	return image;
    }

    public void lookAt(double x, double y)
    {
    	double d_x = this.x - x;
        double d_y = this.y - y;
        //direction = 180 + Math.atan2(d_y, d_x) * 180 / Math.PI;
        direction = Math.PI + Math.atan2(d_y, d_x);
    }
    
    public void direct(double x, double y)
    {
        direction = Math.PI + Math.atan2(y, x);
    }

    public void setDirection(double dir)
    {
    	direction = dir%(Math.PI*2);
    }
    
    public Rectangle getCollider()
    {
        return new Rectangle((int)x, (int)y, width, height);
    }
    
    public AffineTransform getAffineTransform()
    {
    	AffineTransform trans = new AffineTransform();
        trans.translate(x, y);
        //trans.rotate(Math.toRadians(direction), width/2, height/2);
        trans.rotate(direction, width/2, height/2);

        return trans;
    }
    
    abstract String imageFileName();
    
}
