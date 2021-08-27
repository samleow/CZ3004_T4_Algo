package src;

import java.util.List;

public class ExhaustiveSearch {
    double x;
    double y;
    double cost;
    List<car_position> Neighbours;
    List<car_position> Path;
    public ExhaustiveSearch(double x,double y, double cost, List<car_position> Neighbours, List<car_position> Path){
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.Neighbours = Neighbours;
        this.Path = Path;

    }
    public  List<car_position> getNeighbours()
    {
        return Neighbours;
    }
    public  List<car_position> getPath()
    {
        return Path;
    }
    public double getcost()
    {
        return cost;
    }

}
