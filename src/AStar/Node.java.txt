package src.AStar;
import java.util.ArrayList;
import java.util.List;

public class Node{
    private static final int North=1, East= 2, South = 3, West =4;
    public int i, j;
    public Node parent;
    public int heuristicCost;
    public int finalCost;
    public boolean solution;
    public String movement;
    public int orientation;
    public Node (int i, int j){
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString(){
        return "[" + i +"," + j + "]";
    }
}