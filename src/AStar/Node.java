package src.AStar;
import java.util.ArrayList;
import java.util.List;

public class Node{
    public int i, j;
    public Node parent;
    public int heuristicCost;
    public int finalCost;
    public boolean solution;
    public Node (int i, int j){
        this.i = i;
        this.j = j;
    }

    @Override
    public String toString(){
        return "[" + i +"," + j + "]";
    }
}