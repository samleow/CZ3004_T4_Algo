package src;
import java.lang.Math;

public class car_position extends obstacle{
    Double car_facing;
    boolean visited;
    public car_position(double x, double y, Orientation orientation){
        super(x, y, orientation);
        if (orientation== Orientation.South)
        {
            this.x = (double) (x + 0.5);
            this.y = (double) (y-3);
            car_facing = (Math.PI)/2;
            visited = false;
        }
        else if (orientation == Orientation.North)
        {
            this.x = (double) (x + 0.5);
            this.y = (double) (y+4);
            car_facing = -(Math.PI)/2;
            visited = false;
        }
        else if (orientation == Orientation.East)
        {
            this.x = (double) (x + 4);
            this.y = (double) (y+0.5);
            car_facing = Math.PI;
            visited = false;
        }
        else if (orientation == Orientation.West)
        {
            this.x = (double) (x - 3);
            this.y = (double) (y+0.5);
            car_facing =(Double) Math.PI-Math.PI;
            visited = false;
        }
        else
        {
            System.out.println("Orientation was not found");
        }

    }
    public boolean getvisited()
    {
        return visited;
    }
    public void setvisited(boolean visited)
    {
        this.visited = visited;
    }
    
}
