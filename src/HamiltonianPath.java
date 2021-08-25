package algo.src;

import java.util.ArrayList;
import java.util.List;

import algo.src.obstacle.Orientation;

public class HamiltonianPath {
    public HamiltonianPath(){
        List<obstacle> obstacles = new ArrayList<obstacle>();
        obstacles.add(0,new obstacle(1,1,Orientation.North));
        List<car_position> positions = new ArrayList<car_position>();
        positions.add(0,new car_position(1,1,Orientation.North));
        for (car_position cp:positions){
            System.out.println(cp.x);
        }
    }
    public static void main(String args[])
    {
        HamiltonianPath a = new HamiltonianPath();
        
    }
    
}
