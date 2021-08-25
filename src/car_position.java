package algo.src;
import java.lang.Math;

public class car_position extends obstacle{
    double car_facing;
    boolean visited;
    public car_position(float x, float y, Orientation orientation){
        super(x, y, orientation);
        if (orientation== Orientation.South)
        {
            this.x = (float) (x + 0.5);
            this.y = (float) (y-3);
            car_facing = (Math.PI)/2;
        }
        else if (orientation == Orientation.North)
        {
            this.x = (float) (x + 0.5);
            this.y = (float) (y+4);
            car_facing = -(Math.PI)/2;
        }
        else if (orientation == Orientation.East)
        {
            this.x = (float) (x + 4);
            this.y = (float) (y+0.5);
            car_facing = Math.PI;
        }
        else if (orientation == Orientation.West)
        {
            this.x = (float) (x - 3);
            this.y = (float) (y+0.5);
            car_facing =0;
        }
        else
        {
            System.out.println("Orientation was not found");
        }

    }
    
}
