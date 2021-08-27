package src;

import java.util.ArrayList;
import java.util.List;

import src.obstacle.Orientation;

public class HamiltonianPath {
    public HamiltonianPath(){
        List<obstacle> obstacles = new ArrayList<obstacle>();
        obstacles.add(0,new obstacle(1,1,Orientation.North));
        List<car_position> positions = new ArrayList<car_position>();
        positions.add(new car_position(1,1,Orientation.West));
        positions.add(new car_position(8,5,Orientation.North));
        positions.add(new car_position(7,3,Orientation.East));
        positions.add(new car_position(3,3,Orientation.West));
        List<car_position> Shortestpath = new ArrayList<car_position>();
        car_position start = new car_position(1, 4.5, Orientation.South);
        double total_dist = 0;
        car_position temp = start;
        for (car_position cp:positions){            
            double traveldist = Math.sqrt(2*Math.pow(20, 2));
            for (car_position ac:positions)
            {
                Double pythagoras = Math.sqrt(Math.pow(start.x - ac.x, 2)+ Math.pow(start.y - ac.y,2));
                if (pythagoras < traveldist && ac.visited != true)
                {
                    traveldist = pythagoras;
                    temp = ac;
                }
            }
            total_dist += traveldist;
            temp.setvisited(true);
            start = temp;
            Shortestpath.add(temp); 
        }
        System.out.println(Shortestpath);
        System.out.println(total_dist);
        List<ExhaustiveSearch> Double_check = new ArrayList<ExhaustiveSearch>();
        List<car_position> Path = new ArrayList<car_position>();
        ExhaustiveSearch item;
        Double_check.add(new ExhaustiveSearch(1.5,1.5,0,positions,Path));
        while (Double_check.size() != 0)
        {
            item = Double_check.get(0);
            
            if(item.Neighbours.size() == 0)
            {
                break;
            }
            else
            {
                Double_check.remove(0);
                for (int i = 0;i < item.Neighbours.size();i++)
                {
                    Double new_x = item.getNeighbours().get(i).x;
                    Double new_y = item.getNeighbours().get(i).y;
                    Double new_cost = item.getcost() + Math.sqrt(Math.pow(item.x - new_x, 2)+ Math.pow(item.y - new_y,2));
                    List<car_position> New_Path = new ArrayList<car_position>(item.getPath());
                    New_Path.add(item.getNeighbours().get(i));
                    List<car_position> New_Neighbours = new ArrayList<car_position>(item.getNeighbours());
                    New_Neighbours.remove(i);
                    Double_check.add(new ExhaustiveSearch(new_x,new_y,new_cost,New_Neighbours,New_Path));

                }
            }
        }
        

    }
    public static void main(String args[])
    {
        HamiltonianPath a = new HamiltonianPath();
        
    }
    
}
